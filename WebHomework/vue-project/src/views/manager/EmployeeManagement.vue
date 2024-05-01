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
            @selection-change="handleSelectionChange" :row-key="getRowKeys">
            <el-table-column type="selection" width="55" align="center" reserve-selection></el-table-column>
            <el-table-column prop="name" label="姓名" align="center" sortable></el-table-column>
            <el-table-column prop="avatar" label="图片" align="center">
                <template v-slot="{ row }">
                    <img :src="row.avatar" alt="avatar" style="width: 50px; height: 50px; border-radius: 50%;">
                </template>
            </el-table-column>
            <el-table-column prop="gender" label="性别" align="center"></el-table-column>
            <el-table-column prop="position" label="职位" align="center"></el-table-column>
            <el-table-column prop="entryDate" label="入职日期" align="center"></el-table-column>
            <el-table-column prop="updateTime" label="最后操作时间" align="center"></el-table-column>
            <el-table-column label="操作" align="center" width="220px">
                <template v-slot="{ row }">
                    <el-button size="mini" type="primary" plain @click="handleEdit(row)">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="del(row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
        <el-dialog title="员工信息" :visible.sync="fromVisible">
            <el-form :model="form" label-width="80px" ref="employeeForm">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="图片" prop="avatar">
                    <el-upload class="avatar-uploader" action="#" :show-file-list="false"
                        :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="form.avatar" :src="form.avatar" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-radio-group v-model="form.gender">
                        <el-radio label="男">男</el-radio>
                        <el-radio label="女">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="职位" prop="position">
                    <el-select v-model="form.position" placeholder="请选择职位">
                        <el-option label="班主任" value="班主任"></el-option>
                        <el-option label="讲师" value="讲师"></el-option>
                        <el-option label="学工主管" value="学工主管"></el-option>
                        <el-option label="教研主管" value="教研主管"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="入职日期" prop="hireDate">
                    <el-date-picker v-model="form.hireDate" type="date" placeholder="请选择入职日期"></el-date-picker>
                </el-form-item>
                <!-- 其他表单项 -->

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
    name: "EmployeeManagement",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            searchContext: '',
            total: 0,
            ids: [],
            fromVisible: false,
            form: {
                name: '',
                avatar: '', // 图片地址
                gender: '男',
                position: '',
                hireDate: '' // 入职日期
                // 其他表单项...
            }
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
                this.$request.delete('/employee/delete/batch', { data: this.ids }).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => { })
        },
        del(id) {
            this.$confirm('您确认删除吗？', '确认删除', { type: "warning" }).then(() => {
                this.$request.delete('/employee/delete/' + id).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                })
            }).catch(() => { })
        },
        handleAdd() {
            this.fromVisible = true; // 打开新增员工的弹窗
            this.form = {}; // 清空表单数据
        },
        handleEdit(row) {
            this.fromVisible = true; // 打开编辑员工的弹窗
            this.form = JSON.parse(JSON.stringify(row)); // 深拷贝数据，避免直接修改表格中的数据影响到弹窗中的数据
        },
        load(pageNum) {
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/employee/selectByPage', {
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
        handleSelectionChange(rows) {
            this.ids = rows.map(v => v.id)
        },
        handleSizeChange(pageSize) {
            this.pageSize = pageSize
        },
        handleCurrentChange(pageNum) {
            this.load(pageNum)
        },
        getRowKeys(row) {
            return row.id
        },
        save() {
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.form.updateTime = this.getCurrentDateTime();
                    this.$request({
                        url: this.form.id ? '/employee/update' : '/employee/add',
                        method: this.form.id ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {
                            this.$message.success('保存成功');
                            this.fromVisible = false;
                            this.load(); // 重新加载员工列表
                        } else {
                            this.$message.error(res.msg);
                        }
                    });
                }
            });
        }
    }
}
</script>

<style scoped></style>