<template>
    <div>
        <div>
            <el-input style="width: 200px; margin: 0 5px" placeholder="查询" v-model="searchContext" clearable></el-input>
            <el-button type="primary" @click="save(1)">查询</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
            <el-button type="danger" plain @click="delBatch">批量删除</el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            @selection-change="handleSelectionChange" :row-key="getRowKeys">
            <el-table-column type="selection" width="55" align="center" reserve-selection></el-table-column>
            <el-table-column prop="name" label="姓名" align="center" sortable></el-table-column>
            <el-table-column prop="id" label="学号" width="70" align="center" sortable></el-table-column>
            <el-table-column prop="class" label="班级"></el-table-column>
            <el-table-column prop="gender" label="性别"></el-table-column>
            <el-table-column prop="phone" label="手机号"></el-table-column>
            <el-table-column prop="topDegree" label="最高学历"></el-table-column>
            <el-table-column prop="disciplinaryNumber" label="违纪次数"></el-table-column>
            <el-table-column prop="disciplinaryScore" label="违纪扣分"></el-table-column>
            <el-table-column prop="updateTime" label="最后操作时间"></el-table-column>
            <el-table-column label="操作" align="center" width="220px">
                <template v-slot="scope">
                    <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" type="warning" plain @click="disciplinary(scope.row.id)">违纪</el-button>
                    <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[5, 10, 20]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
        <!-- 编辑个人信息 -->
        <el-dialog title="个人信息" :visible.sync="fromVisible" width="30%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px" ref="formRef">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item label="学号" prop="id">
                    <el-input v-model="form.id" placeholder="学号"></el-input>
                </el-form-item>
                <el-form-item label="班级" prop="class">
                    <el-input v-model="form.class" placeholder="班级"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-radio-group v-model="form.gender">
                        <el-radio label="男"></el-radio>
                        <el-radio label="女"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="form.phone" placeholder="手机号"></el-input>
                </el-form-item>
                <el-form-item label="最高学历" prop="topDegree">
                    <el-input v-model="form.topDegree" placeholder="最高学历"></el-input>
                </el-form-item>
                <el-form-item label="违纪次数" prop="disciplinaryNumber">
                    <el-input v-model="form.disciplinaryNumber" placeholder="违纪次数"></el-input>
                </el-form-item>
                <el-form-item label="违纪扣分" prop="disciplinaryScore">
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
            <el-form :model="disciplinaryForm" label-width="80px" style="padding-right: 20px" :rules="disciplinaryRules"
                ref="disciplinaryFormRef">
                <el-form-item label="扣分分数" prop="score">
                    <el-input v-model="disciplinaryForm.score" placeholder="请输入扣分分数"></el-input>
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
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            searchContext: '',
            total: 0,
            fromVisible: false,
            form: {},
            disciplinaryVisible: false,
            disciplinaryForm: {
                score: ''
            },
            disciplinaryRules: {
                score: [
                    { required: true, message: '请输入扣分分数', trigger: 'blur' },
                    { pattern: /^[0-9]+$/, message: '扣分分数必须为数字', trigger: 'blur' }
                ]
            },
            user: JSON.parse(localStorage.getItem('local-user') || '{}'),
            ids: []
        }
    },
    created() {
        this.load()
    },
    methods: {
        disciplinary(row) {
            this.disciplinaryVisible = true;
            this.disciplinaryForm.score = '';
            this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
        },
        handleDisciplinary() {
            this.$refs.disciplinaryFormRef.validate((valid) => {
                if (valid) {
                    // 执行违纪处分逻辑
                    this.form.disciplinaryNumber += 1;
                    this.form.disciplinaryScore += this.disciplinaryForm.score;
                    this.save();
                    this.disciplinaryVisible = false;
                }
            });
        },
        delBatch() {
            if (!this.ids.length) {
                this.$message.warning('请选择数据')
                return
            }
            this.$confirm('您确认批量删除这些数据吗？', '确认删除', { type: "warning" }).then(() => {
                this.$request.delete('/user/delete/batch', { data: this.ids }).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => { })
        },
        handleSelectionChange(rows) {   // 当前选中的所有的行数据
            this.ids = rows.map(v => v.id)
        },
        del(id) {
            this.$confirm('您确认删除吗？', '确认删除', { type: "warning" }).then(() => {
                this.$request.delete('/user/delete/' + id).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => { })
        },
        handleEdit(row) {   // 编辑数据
            this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
            this.fromVisible = true   // 打开弹窗
        },
        handleAdd() {   // 新增数据
            this.fromVisible = true   // 打开弹窗
        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.form.updateTime = this.getCurrentDateTime();
                    this.$request({
                        url: this.form.id ? '/student/update' : '/student/add',
                        method: this.form.id ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {  // 表示成功保存
                            this.$message.success('保存成功')
                            this.fromVisible = false
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }
            })
        },
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/student/selectByPage', {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.searchContext
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        handleSizeChange(pageSize) {//改变当前页面数量
            this.pageSize = pageSize
        },
        getRowKeys(row) {
            return row.id
        },
        getCurrentDateTime() {
            const now = new Date();
            const year = now.getFullYear();
            const month = String(now.getMonth() + 1).padStart(2, '0');
            const day = String(now.getDate()).padStart(2, '0');
            const hours = String(now.getHours()).padStart(2, '0');
            const minutes = String(now.getMinutes()).padStart(2, '0');
            const seconds = String(now.getSeconds()).padStart(2, '0');

            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        }

    }
}

</script>

<style scoped></style>