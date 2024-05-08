<template>
    <div>
        <div>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="学员姓名">
                    <el-input v-model="searchName" placeholder="请输入班级名称"></el-input>
                </el-form-item>
                <el-form-item label="学号">
                    <el-input v-model="searchId" placeholder="请输入学号"></el-input>
                </el-form-item>
                <el-form-item label="最高学历">
                    <el-select v-model="searchDegree" placeholder="请选择">
                        <el-option label="初中" value="1"></el-option>
                        <el-option label="高中" value="2"></el-option>
                        <el-option label="大专" value="3"></el-option>
                        <el-option label="本科" value="4"></el-option>
                        <el-option label="硕士" value="5"></el-option>
                        <el-option label="博士" value="6"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所属班级">
                    <el-select v-model="searchClassId" placeholder="请选择">
                        <el-option v-for="entityClass in classList" :key="entityClass.classId"
                            :label="entityClass.className" :value="entityClass.classId"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="search">查询</el-button>

                </el-form-item>
            </el-form>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增班级</el-button>
            <el-button type="danger" plain @click="delBatch">批量删除</el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            @selection-change="handleSelectionChange" :row-key="getRowKeys">
            <el-table-column type="selection" width="45" align="center" reserve-selection></el-table-column>
            <el-table-column prop="name" label="姓名" align="center" width="100"></el-table-column>
            <el-table-column prop="studentId" label="学号" width="150" align="center" sortable></el-table-column>
            <el-table-column prop="classId" label="班级" width="100" align="center">
                <template slot-scope="scope">
                    {{ getClassName(scope.row.classId) }}
                </template>
            </el-table-column>
            <el-table-column prop="gender" label="性别" width="100" align="center">
                <template slot-scope="scope">
                    {{ scope.row.gender === 1 ? '男' : '女' }}
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="手机号" align="center"></el-table-column>
            <el-table-column prop="topDegree" label="最高学历" align="center">
                <template slot-scope="scope">
                    {{ scope.row.topDegree == 1 ? '初中' : scope.row.topDegree == 2 ? '高中' : scope.row.topDegree == 3 ?
                        '大专' : scope.row.topDegree == 4 ? '本科'
                            : scope.row.topDegree == 5 ? '硕士' : scope.row.topDegree == 6 ? '博士' : '其他'
                    }}
                </template>
            </el-table-column>
            <el-table-column prop="disciplinaryNumber" label="违纪次数" align="center"></el-table-column>
            <el-table-column prop="disciplinaryScore" label="违纪扣分" align="center"></el-table-column>
            <el-table-column prop="updateTime" label="最后操作时间" align="center">
                <template slot-scope="scope">
                    {{
                        scope.row.updateTime.replace('T', ' ')
                    }}
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="220px">
                <template v-slot="scope">
                    <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" type="warning" plain @click="disciplinary(scope.row)">违纪</el-button>
                    <el-button size="mini" type="danger" plain @click="del(scope.row.studentId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
        <!-- 编辑个人信息 -->
        <el-dialog title="个人信息" :visible.sync="fromVisible" width="30%">
            <el-form :model="form" :rules="rules" label-width="80px" style="padding-right: 20px">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="学号" prop="studentId" v-if="form.updateTime">
                    <el-input v-model="form.studentId" placeholder="请输入学号" disabled></el-input>
                </el-form-item>
                <el-form-item label="学号" prop="studentId" v-else>
                    <el-input v-model="form.studentId" placeholder="请输入学号"></el-input>
                </el-form-item>
                <el-form-item label="班级" prop="classId">
                    <el-select v-model="form.classId" placeholder="请选择班级">
                        <el-option v-for="entityClass in classList" :key="entityClass.classId"
                            :label="entityClass.className" :value="entityClass.classId"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-radio-group v-model="form.gender">
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="出生日期" prop="dateOfBirth">
                    <el-date-picker value="form.dateOfBirth" v-model="form.dateOfBirth" type="date"
                        placeholder="请选择出生日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="form.phone" placeholder="手机号"></el-input>
                </el-form-item>
                <el-form-item label="最高学历" prop="topDegree">
                    <el-select v-model="form.topDegree" placeholder="请选择最高学历">
                        <el-option label="初中" :value="1"></el-option>
                        <el-option label="高中" :value="2"></el-option>
                        <el-option label="大专" :value="3"></el-option>
                        <el-option label="本科" :value="4"></el-option>
                        <el-option label="硕士" :value="5"></el-option>
                        <el-option label="博士" :value="6"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="违纪次数" prop="disciplinaryNumber" v-if="form.updateTime">
                    <el-input v-model="form.disciplinaryNumber" placeholder="违纪次数"></el-input>
                </el-form-item>
                <el-form-item label="违纪扣分" prop="disciplinaryScore" v-if="form.updateTime">
                    <el-input v-model="form.disciplinaryScore" placeholder="违纪扣分"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="fromVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <!-- 违纪处分对话框 -->
        <el-dialog title="违纪处分" :visible.sync="disciplinaryVisible" width="30%">
            <el-form :model="disciplinaryForm" label-width="80px" style="padding-right: 20px">
                <el-form-item label="扣分分数" prop="score">
                    <el-input v-model="disciplinaryForm.score" placeholder="请输入扣分分数" type="number"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="disciplinaryVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleDisciplinary">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

export default {
    name: "StudentManagement",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            searchName: '',
            searchId: '',
            searchDegree: '',
            searchClassId: '',
            dateRange: "",
            total: 0,
            ids: [],
            fromVisible: false,
            form: {},
            classList: [],
            disciplinaryVisible: false,
            disciplinaryForm: {
                score: 0
            },
            rules: {
                name: [
                    { required: true, message: '请输入姓名', trigger: 'blur' },
                    { pattern: /^[\u4E00-\u9FA5a-zA-Z0-9]{2,10}$/, message: '姓名只能包含汉字、数字和字母，长度为2-10', trigger: 'blur' }
                ],
                studentId: [
                    { required: true, message: '请输入学号', trigger: 'blur' },
                    { pattern: /^[a-zA-Z0-9]{10}$/, message: '学号必须为10位数字或字母', trigger: 'blur' }
                ],
                gender: [
                    { required: true, message: '请选择性别', trigger: 'change' }
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { pattern: /^[0-9]{11}$/, message: '手机号必须为11位数字', trigger: 'blur' }
                ],
                classId: [
                    { required: true, message: '请选择班级', trigger: 'change' }
                ]
            },
        }
    },
    created() {
        this.load()
    },
    methods: {
        search() {
            this.load()
        },
        delBatch() {
            if (!this.ids.length) {
                this.$message.warning('请选择数据')
                return
            }
            this.$confirm('您确认批量删除这些数据吗？', '确认删除', { type: "warning" }).then(() => {
                this.$request.delete(`/student/${this.ids}`).then(res => {

                    if (res.code === 200) {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => { })
        },
        disciplinary(row) {
            this.disciplinaryVisible = true;
            this.disciplinaryForm.score = '';
            this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
        },
        handleDisciplinary() {
            // 执行违纪处分逻辑
            this.form.disciplinaryNumber += 1;
            this.form.disciplinaryScore += parseInt(this.disciplinaryForm.score);
            this.save();
            this.disciplinaryVisible = false;

        },
        del(id) {
            this.$confirm('您确认删除吗？', '确认删除', { type: "warning" }).then(() => {
                this.$request.delete(`/student/${id}`).then(res => {
                    if (res.code === 200) {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => { })
        },
        handleAdd() {
            this.fromVisible = true; // 打开新增班级的弹窗
            this.form = {}; // 清空表单数据
        },
        handleEdit(row) {
            this.form = JSON.parse(JSON.stringify(row)); // 深拷贝数据，避免直接修改表格中的数据影响到弹窗中的数据
            this.fromVisible = true; // 打开编辑班级的弹窗
            console.log(22222222222, this.form);
        },
        load() {
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                name: this.searchName,
                id: this.searchId,
                degree: this.searchDegree,
                classId: this.searchClassId,
                begin: this.dateRange[0],
                end: this.dateRange[1],
            }
            this.$request.get("class").then(res => {
                this.classList = res.data.row
                this.$request.get(this.makeUrl(params)).then(res => {
                    this.tableData = res.data.row
                    this.total = res.data.total
                    //请求班主任
                })
            })
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            this.pageNum = pageNum
            this.load()
        },
        getRowKeys(row) {
            return row.id
        },
        handleSelectionChange(rows) {
            this.ids = rows.map(v => v.studentId)
        },
        save() {
            this.$request({
                url: '/student',
                method: this.form.updateTime ? 'PUT' : 'POST',
                data: this.form
            }).then(res => {
                if (res.code === 200) {
                    this.$message.success('保存成功');
                    this.fromVisible = false;
                    this.load(); // 重新加载班级列表
                } else {
                    this.$message.error(res.msg);
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
            if (params.name !== '') {
                paramsList.push(`&name=${encodeURIComponent(params.name)}`);
            }
            if (params.name !== '') {
                paramsList.push(`&studentId=${encodeURIComponent(params.id)}`);
            }
            if (params.name !== '') {
                paramsList.push(`&topDegree=${encodeURIComponent(params.degree)}`);
            }
            if (params.name !== '') {
                paramsList.push(`&classId=${encodeURIComponent(params.classId)}`);
            }
            if (params.begin) {
                paramsList.push(`&begin=${encodeURIComponent(params.begin)}`);
            }
            if (params.end) {
                paramsList.push(`&end=${encodeURIComponent(params.end)}`);
            }
            const url = `/student?${paramsList.join("")}`;
            return url
        },
        getClassName(id) {
            const foundElement = this.classList.find(item => item.classId === id);
            return foundElement.className;
        },
    }
}
</script>

<style scoped></style>