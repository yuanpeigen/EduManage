<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
        </div>
        <el-table width="80%" :data="tableData" stripe
            :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            @selection-change="handleSelectionChange" :row-key="getRowKeys">
            <el-table-column type="index" label="序号" width="100"></el-table-column>
            <el-table-column prop="departmentName" label="部门名称" align="center"></el-table-column>
            <el-table-column prop="updateTime" label="最后操作时间" align="center">
                <template slot-scope="scope">
                    {{
                        scope.row.updateTime = scope.row.updateTime.replace('T', ' ')
                    }}
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="200px">
                <template v-slot="scope">
                    <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="del(scope.row.departmentId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 编辑或新增部门信息 -->
        <el-dialog title="部门信息" :visible.sync="fromVisible" width="30%">
            <el-form :model="form" :rules="rules" label-width="80px" style="padding-right: 20px" ref="formRef">
                <el-form-item label="部门名称" prop="departmentName">
                    <el-input v-model="form.departmentName" placeholder="部门名称"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="fromVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "DepartmentManagement",
    data() {
        return {
            tableData: [],
            fromVisible: false,
            form: {},
            ids: [],
            rules: {
                departmentName: [
                    { required: true, message: '部门名称不能为空', trigger: 'blur' },
                    { pattern: /^[\u4e00-\u9fa5a-zA-Z0-9]{2,10}$/, message: '长度应为2到10位,且只能包含汉字、字母和数字', trigger: 'blur' }
                ]
            }
        }
    },
    created() {
        this.load()
    },
    methods: {
        handleAdd() {
            this.fromVisible = true
            this.form = {}
        },
        save() {
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.$request({
                        url: this.form.departmentId ? '/departments' : '/departments',
                        method: this.form.departmentId ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {

                        if (res.code === 200) {
                            this.$message.success('保存成功')
                            this.fromVisible = false
                            this.load()
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }
            })
        },
        load() {
            this.$request.get('/departments').then(res => {
                //处理数据
                this.tableData = res.data
            })
        },
        getRowKeys(row) {
            return row.departmentId
        },
        handleEdit(row) {
            this.form = { departmentName: row.departmentName, departmentId: row.departmentId }
            this.fromVisible = true
        },
        del(departmentId) {
            this.$confirm('确认删除该部门吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$request.delete(`/departments/${departmentId}`).then(res => {
                    if (res.code === 200) {
                        this.$message.success('删除成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                }).catch(() => { this.$message.error("这个部门还有员工呢!") })
            })
        },
        handleSelectionChange(rows) {
            this.ids = rows.map(v => v.departmentId)
        },
    }
}
</script>

<style scoped></style>