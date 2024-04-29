<template>
    <div>
        <div>
            <el-input style="width: 200px; margin: 0 5px" placeholder="查询" v-model="searchContext" clearable></el-input>
            <el-button type="primary" @click="search">查询</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
            <el-button type="danger" plain @click="delBatch">批量删除</el-button>
        </div>
        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            @selection-change="handleSelectionChange" max-height="410" :row-key="getRowKeys">
            <el-table-column type="selection" width="55" align="center" reserve-selection></el-table-column>
            <el-table-column prop="name" label="姓名" sortable></el-table-column>
            <el-table-column prop="id" label="学号" width="70" align="center" sortable></el-table-column>
            <el-table-column prop="class" label="班级"></el-table-column>
            <el-table-column prop="gender" label="性别"></el-table-column>
            <el-table-column prop="phone" label="手机号"></el-table-column>
            <el-table-column prop="topDegree" label="最高学历"></el-table-column>
            <el-table-column prop="disciplinaryNumber" label="违纪次数"></el-table-column>
            <el-table-column prop="disciplinaryScore" label="违纪扣分"></el-table-column>
            <el-table-column prop="updateTime" label="最后操作时间"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column label="操作" align="center" width="180">
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

        <el-dialog title="个人信息" :visible.sync="fromVisible" width="30%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" placeholder="姓名"></el-input>
                </el-form-item>
                <!-- 后面再补齐 -->



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
    name: "StudentManagement",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            searchContext: '',
            total: 0,
            fromVisible: false,
            form: {},
            user: JSON.parse(localStorage.getItem('local-user') || '{}'),
            rules: {
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ]
            },
            ids: []
        }
    },
    created() {
    },
    methods: {
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
                    this.$request({
                        url: this.form.id ? '/user/update' : '/user/add',
                        method: this.form.id ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {  // 表示成功保存
                            this.$message.success('保存成功')
                            this.fromVisible = false
                            this.form.avatar = ""
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }
            })
        },
        //查询函数(todo)
        search() {  // 分页查询

        },
        handleCurrentChange(pageNum) {
            this.search(pageNum)
        },
        handleSizeChange(pageSize) {//改变当前页面数量
            this.pageSize = pageSize
        },
        getRowKeys(row) {
            return row.id
        },
        // handleAvatarSuccess(response, file, fileList) {
        //     // 把user的头像属性换成上传的图片的链接
        //     this.form.avatar = response.data
        // },
    }
}
</script>

<style scoped></style>