<template>
    <div>
        <div>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="课程名称">
                    <el-select v-model="searchCourseId" filterable clearable placeholder="请选择课程">
                        <el-option v-for="course in courses" :key="course.courseId" :label="course.courseName"
                            :value="course.courseId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="班级">
                    <el-select v-model="searchClassId" filterable clearable placeholder="请选择班级">
                        <el-option v-for="entityClass in classes" :key="entityClass.classId"
                            :label="entityClass.className" :value="entityClass.classId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="教师">
                    <el-select v-model="searchTeacherId" filterable clearable placeholder="请选择教师">
                        <el-option v-for="teacher in teachers" :key="teacher.employeeId" :label="teacher.name"
                            :value="teacher.employeeId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search">查询</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增课程安排</el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            :row-key="getRowKeys">
            <el-table-column label="序号" align="center" type="index" :index="indexMethod"></el-table-column>
            <el-table-column prop="courseId" label="课程名称" align="center">
                <template slot-scope="scope">
                    {{ getCourseName(scope.row.courseId) }}
                </template>
            </el-table-column>
            <el-table-column prop="classId" label="班级名称" align="center">
                <template slot-scope="scope">
                    {{ getClassName(scope.row.classIds) }}
                </template>
            </el-table-column>
            <el-table-column prop="teacherId" label="教师名称" align="center">
                <template slot-scope="scope">
                    {{ getTeacherName(scope.row.teacherIds) }}
                </template>
            </el-table-column>

            <el-table-column label="操作" align="center" width="220px">
                <template v-slot="{ row }">
                    <el-button size="mini" type="primary" plain @click="handleEdit(row.courseId)">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="delBatch(row.courseId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
        <el-dialog title="课程安排信息" :visible.sync="fromVisible">
            <el-form :model="form" :rules="rules" label-width="80px" ref="scheduleForm">
                <el-form-item label="课程名称" prop="courseId">
                    <el-select v-model="form.courseId" filterable clearable placeholder="请选择课程">
                        <el-option v-for="item in courses" :key="item.courseId" :label="item.courseName"
                            :value="item.courseId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="班级名称" clearable prop="classId">
                    <el-select v-model="form.classId" filterable placeholder="请选择班级">
                        <el-option v-for="item in classes" :key="item.classId" :label="item.className"
                            :value="item.classId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="教师名称" clearable prop="teacherId">
                    <el-select v-model="form.teacherId" filterable placeholder="请选择教师">
                        <el-option v-for="item in teachers" :key="item.employeeId" :label="item.name"
                            :value="item.employeeId"></el-option>
                    </el-select>
                </el-form-item>

                <!-- 按钮 -->
                <el-form-item>
                    <el-button type="primary" @click="save">保存</el-button>
                    <el-button @click="fromVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <el-dialog title="课程安排编辑" :visible.sync="courseScheduleVisible">
            <el-table :data="tableCourseData" stripe
                :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }" :row-key="getRowKeys">
                <el-table-column prop="courseId" label="课程名称" align="center">
                    <template slot-scope="scope">
                        {{ getCourseName(scope.row.courseId) }}
                    </template>
                </el-table-column>
                <el-table-column prop="classId" label="班级名称" align="center">
                    <template slot-scope="scope">
                        {{ getClassName(scope.row.classId) }}
                    </template>
                </el-table-column>
                <el-table-column prop="teacherId" label="教师名称" align="center">
                    <template slot-scope="scope">
                        {{ getTeacherName(scope.row.teacherId) }}
                    </template>
                </el-table-column>

                <el-table-column label="操作" align="center" width="220px">
                    <template v-slot="{ row }">
                        <el-button size="mini" type="primary" plain @click="handleSingleEdit(row)">编辑</el-button>
                        <el-button size="mini" type="danger" plain @click="del(row.scheduleId)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "ScheduleManagement",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            searchCourseId: null,
            searchClassId: null,
            searchTeacherId: null,
            total: 0,
            ids: [],
            fromVisible: false,
            courseScheduleVisible: false,
            form: {},
            courses: [],
            classes: [],
            teachers: [],
            courseScheduleList: [],//数据库的单一
            tableCourseData: [],
            rules: {
                courseId: [
                    { required: true, message: '请选择课程', trigger: 'change' }
                ],
                classId: [
                    { required: true, message: '请选择班级', trigger: 'change' }
                ],
                teacherId: [
                    { required: true, message: '请选择教师', trigger: 'change' }
                ]
            },
        }
    },
    created() {
        this.loadCourses();
        this.loadClasses();
        this.loadTeachers();
        this.load();
    },
    methods: {

        search() {
            this.load();
        },
        delBatch(id) {
            this.ids = this.courseScheduleList.filter(obj => obj.courseId === id).map(obj => obj.scheduleId);
            console.log(34343434, this.ids);
            this.$confirm('您确认删除这门课程的安排吗？', '确认删除', { type: "warning" }).then(() => {
                this.$request.delete(`/schedule/${this.ids}`).then(res => {
                    if (res.code === 200) {   // 表示操作成功
                        this.$message.success('操作成功');
                        this.load();
                    } else {
                        this.$message.error(res.msg);  // 弹出错误的信息
                    }
                });
            }).catch(() => { });
        },
        del(id) {
            this.$confirm('您确认删除吗？', '确认删除', { type: "warning" }).then(() => {
                this.$request.delete(`/schedule/${id}`).then(res => {
                    if (res.code === 200) {   // 表示操作成功
                        this.$message.success('操作成功');
                        this.load();
                    } else {
                        this.$message.error(res.msg);  // 弹出错误的信息
                    }
                });
            }).catch(() => {
                this.$message.error("无法删除此安排！");
            });
        },
        handleAdd() {
            this.fromVisible = true; // 打开新增课程安排的弹窗
            this.form = {}; // 清空表单数据
        },
        handleEdit(id) {
            this.tableCourseData = this.courseScheduleList.filter(item => item.courseId === id)
            this.courseScheduleVisible = true; // 打开编辑课程安排的弹窗

        },
        handleSingleEdit(row) {
            this.form = JSON.parse(JSON.stringify(row)); // 深拷贝数据，避免直接修改表格中的数据影响到弹窗中的数据
            this.courseScheduleVisible = false
            this.fromVisible = true; // 打开编辑的弹窗
            console.log(121212121212, this.form);
        },

        transformCourseSchedules(scheduleList) {
            scheduleList.forEach(schedule => {
                schedule.classIds = schedule.classIds.split(",").map(Number);
                schedule.teacherIds = schedule.teacherIds.split(",").map(Number);
            });
            return scheduleList
        },

        load() {
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                searchCourseId: this.searchCourseId,
                searchClassId: this.searchClassId,
                searchTeacherId: this.searchTeacherId
            };
            this.$request.get(this.makeUrl(params)).then(res => {
                this.total = res.data.total
                let scheduleList = res.data.row;
                if (scheduleList) {
                    this.tableData = this.transformCourseSchedules(scheduleList)
                }
            });
            this.$request.get("schedule/all").then(res => {
                // console.log(res.data);
                this.courseScheduleList = res.data
                console.log(2222222222222222, this.courseScheduleList)
            }

            )
        },
        loadCourses() {
            this.$request.get('/course/all').then(res => {
                this.courses = res.data;
            });
        },
        loadClasses() {
            this.$request.get('/class/all').then(res => {
                this.classes = res.data;
                console.log(9999, this.classes);
            });
        },
        loadTeachers() {
            this.$request.get('/employee/all').then(res => {
                this.teachers = res.data.filter(item => item.job === 2);
            });
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize;
            this.load();
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum;
            this.load();
        },
        getRowKeys(row) {
            return row.scheduleId;
        },
        save() {
            this.$refs.scheduleForm.validate((valid) => {
                if (valid) {
                    //TODO 要处理多选操作
                    this.$request({
                        url: '/schedule',
                        method: this.form.scheduleId ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === 200) {
                            this.$message.success('保存成功');
                            this.fromVisible = false;
                            this.load(); // 重新加载课程安排列表
                        } else {
                            this.$message.error(res.msg);
                        }
                    });
                }
            });
        },
        makeUrl(params) {
            const paramsList = [];
            if (params.pageNum !== '') {
                paramsList.push(`pageNum=${encodeURIComponent(params.pageNum)}`);
            }
            if (params.pageSize !== '') {
                paramsList.push(`&pageSize=${encodeURIComponent(params.pageSize)}`);
            }
            if (params.searchCourseId) {
                paramsList.push(`&courseId=${encodeURIComponent(params.searchCourseId)}`);
            }
            if (params.searchClassId) {
                paramsList.push(`&classId=${encodeURIComponent(params.searchClassId)}`);
            }
            if (params.searchTeacherId) {
                paramsList.push(`&teacherId=${encodeURIComponent(params.searchTeacherId)}`);
            }
            const url = `/schedule?${paramsList.join("")}`;
            return url;
        },
        indexMethod(index) {
            return index + 1 + this.pageSize * (this.pageNum - 1);
        },
        getTeacherName(ids) {
            let teacherString = [];

            if (typeof ids == 'object') {
                ids.forEach(id => {
                    const foundElement = this.teachers.find(item => item.employeeId === id);
                    teacherString.push(foundElement.name)
                });
                return teacherString.join(", ");
            }
            const foundElement = this.teachers.find(item => item.employeeId === ids);
            return foundElement.name
        },
        getCourseName(id) {
            const foundElement = this.courses.find(item => item.courseId === id);

            return foundElement.courseName;
        },
        getClassName(ids) {
            let classString = [];
            if (typeof ids == 'object') {
                ids.forEach(id => {
                    const foundElement = this.classes.find(item => item.classId === id);
                    classString.push(foundElement.className)
                });
                return classString.join(", ");
            }
            const foundElement = this.classes.find(item => item.classId === ids);
            return foundElement.className
        },
    }
};
</script>

<style scoped></style>