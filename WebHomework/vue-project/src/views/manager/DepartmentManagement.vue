<template>
    <div>
        <div>
            <el-input style="width: 200px; margin: 0 5px" placeholder="查询" v-model="searchContext" clearable></el-input>
            <el-button type="primary" @click="search">查询</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            @selection-change="handleSelectionChange" :row-key="getRowKeys">
            <el-table-column type="index" label="序号"></el-table-column>
            <el-table-column prop="name" label="部门名称" align="center"></el-table-column>
            <el-table-column prop="updateTime" label="最后操作时间" align="center"></el-table-column>
            <el-table-column label="操作" align="center" width="120px">
                <template v-slot="scope">
                    <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
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
        <!-- 编辑部门信息 -->
        <el-dialog title="部门信息" :visible.sync="fromVisible" width="30%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px" ref="formRef">
                <el-form-item label="部门名称" prop="name">
                    <el-input v-model="form.name" placeholder="部门名称"></el-input>
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
            pageNum: 1,
            pageSize: 5,
            searchContext: '',
            total: 0,
            fromVisible: false,
            form: {},
            ids: []
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
                        url: this.form.id ? '/department/update' : '/department/add',
                        method: this.form.id ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {
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
        load(pageNum) {
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/department/selectByPage', {
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
        handleSizeChange(pageSize) {
            this.pageSize = pageSize
        },
        getRowKeys(row) {
            return row.id
        },
        handleEdit(row) {
            this.form = Object.assign({}, row)
            this.fromVisible = true
        },
        del(id) {
            this.$confirm('确认删除该部门吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$request.delete(`/department/delete/${id}`).then(res => {
                    if (res.code === '200') {
                        this.$message.success('删除成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)
                    }
                })
            }).catch(() => { })
        },
        handleSelectionChange(rows) {
            this.ids = rows.map(v => v.id)
        },
        search() {
            this.load()
        }
    }
}
</script>

<style scoped></style>