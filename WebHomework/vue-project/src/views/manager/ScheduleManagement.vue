<template>
    <div>
        <div>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="课程名称">
                    <el-select v-model="searchCourseId" placeholder="请选择课程">
                        <el-option v-for="course in courses" :key="course.courseId" :label="course.courseName"
                            :value="course.courseId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="班级">
                    <el-select v-model="searchClassId" placeholder="请选择班级">
                        <el-option v-for="entityClass in classes" :key="entityClass.classId"
                            :label="entityClass.className" :value="entityClass.classId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="教师">
                    <el-select v-model="searchTeacherId" placeholder="请选择教师">
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
            <el-button type="danger" plain @click="delBatch">批量删除</el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            @selection-change="handleSelectionChange" :row-key="getRowKeys">
            <el-table-column type="selection" width="55" align="center" reserve-selection></el-table-column>
            <el-table-column label="序号" align="center" type="index" :index="indexMethod"></el-table-column>
            <el-table-column prop="courseId" label="课程名称" align="center" sortable>
                <template slot-scope="scope">
                    {{ getCourseName(scope.row.courseId) }}
                </template>
            </el-table-column>
            <el-table-column prop="classId" label="班级名称" align="center" sortable>
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
                    <el-button size="mini" type="primary" plain @click="handleEdit(row)">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="del(row.scheduleId)">删除</el-button>
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
            <el-form :model="form" label-width="80px" ref="scheduleForm">
                <el-form-item label="课程名称" prop="courseId">
                    <el-select v-model="form.courseId" placeholder="请选择课程">
                        <el-option v-for="item in courses" :key="item.courseId" :label="item.courseName"
                            :value="item.courseId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="班级名称" prop="classId">
                    <el-select v-model="form.classId" placeholder="请选择班级">
                        <el-option v-for="item in classes" :key="item.classId" :label="item.className"
                            :value="item.classId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="教师名称" prop="teacherId">
                    <el-select v-model="form.teacherId" placeholder="请选择教师">
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
            form: {},
            courses: [],
            classes: [],
            teachers: []
        }
    },
    created() {
        this.loadCourses();
        this.loadClasses();
        this.loadTeachers();
        this.load();
    },
    methods: {

        handleSelectionChange(rows) {
            this.ids = rows.map(v => v.scheduleId);
        },
        search() {
            this.load();
        },
        delBatch() {
            if (!this.ids.length) {
                this.$message.warning('请选择数据');
                return;
            }
            this.$confirm('您确认批量删除这些数据吗？', '确认删除', { type: "warning" }).then(() => {
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
        handleEdit(row) {
            this.form = JSON.parse(JSON.stringify(row)); // 深拷贝数据，避免直接修改表格中的数据影响到弹窗中的数据
            this.fromVisible = true; // 打开编辑课程安排的弹窗
        },
        transformCourseSchedules(scheduleList) {
            const resultMap = {};
            scheduleList.forEach(schedule => {
                const { courseId, teacherId, classId } = schedule;
                // 如果该 courseId 不存在于 resultMap 中，则初始化它
                if (!resultMap[courseId]) {
                    resultMap[courseId] = {
                        courseId,
                        teacherIds: [],
                        classIds: []
                    };
                }
                // 如果 teacherId 不在数组中，则添加它
                if (!resultMap[courseId].teacherIds.includes(teacherId)) {
                    resultMap[courseId].teacherIds.push(teacherId);
                }

                // 如果 classId 不在数组中，则添加它
                if (!resultMap[courseId].classIds.includes(classId)) {
                    resultMap[courseId].classIds.push(classId);
                }
            });
            console.log(3333333333, Object.values(resultMap));
            // 将 resultMap 转换为数组
            return Object.values(resultMap);
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
                console.log(11111111111111, res.data);
                if (scheduleList) {
                    this.tableData = this.transformCourseSchedules(scheduleList)
                    console.log(22222222222, this.tableData);
                }
            });
        },
        loadCourses() {
            this.$request.get('/course/all').then(res => {
                this.courses = res.data;
            });
        },
        loadClasses() {
            this.$request.get('/class/all').then(res => {
                this.classes = res.data;
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
        getTeacherName(id) {
            const foundElement = this.teachers.find(item => item.employeeId === id);
            return foundElement.name;
        },
        getCourseName(id) {
            const foundElement = this.courses.find(item => item.courseId === id);

            return foundElement.courseName;
        },
        getClassName(id) {
            const foundElement = this.classes.find(item => item.classId === id);
            console.log(555555555555, this.classes, id);
            return foundElement.className;
        },
    }
};
</script>

<style scoped></style>