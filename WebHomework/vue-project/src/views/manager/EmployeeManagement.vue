<template>
    <div>
        <div>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="姓名">
                    <el-input v-model="searchName" placeholder="请输入员工姓名"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-select v-model="searchGender" placeholder="请选择">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="入职时间">
                    <el-date-picker unlink-panels v-model="dateRange" type="daterange" value-format="yyyy-MM-dd"
                        range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                    </el-date-picker>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="search">查询</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增</el-button>
            <el-button type="danger" plain @click="delBatch">批量删除</el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            @selection-change="handleSelectionChange" :row-key="getRowKeys">
            <el-table-column type="selection" width="55" align="center" reserve-selection></el-table-column>
            <el-table-column prop="name" label="姓名" align="center" sortable></el-table-column>
            <el-table-column prop="image" label="图片" align="center">
                <template v-slot="{ row }">
                    <img :src="row.image" alt="avatar" style="width: 50px; height: 50px; border-radius: 50%;">
                </template>
            </el-table-column>
            <el-table-column prop="gender" label="性别" align="center">
                <template slot-scope="scope">
                    {{ scope.row.gender === 1 ? '男' : '女' }}
                </template>
            </el-table-column>
            <el-table-column prop="job" label="职位" align="center">
                <template slot-scope="scope">
                    {{ scope.row.job == 1 ? '班主任' : scope.row.job == 2 ? '讲师' : scope.row.job == 3 ? '学工主管' :
                        scope.row.job == 4 ? '教研主管' : scope.row.job == 5 ? '其他' : '其他'
                    }}
                </template>
            </el-table-column>
            <el-table-column prop="entrydate" label="入职日期" align="center"></el-table-column>
            <el-table-column prop="updateTime" label="最后操作时间" align="center">
                <template slot-scope="scope">
                    {{
                        scope.row.updateTime.replace('T', ' ')
                    }}
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="220px">
                <template v-slot="{ row }">
                    <el-button size="mini" type="primary" plain @click="handleEdit(row)">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="del(row.employeeId)">删除</el-button>
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
                <el-form-item label="图片" prop="image">
                    <el-upload class="avatar-uploader" action="#" :show-file-list="false"
                        :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="form.image" :src="form.image" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-radio-group v-model="form.gender">
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="职位" prop="job">
                    <el-select v-model="form.job" placeholder="请选择职位">
                        <el-option label="班主任" :value="1"></el-option>
                        <el-option label="讲师" :value="2"></el-option>
                        <el-option label="学工主管" :value="3"></el-option>
                        <el-option label="教研主管" :value="4"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="入职日期" prop="entryDate">
                    <el-date-picker value="form.entrydate" v-model="form.entrydate" type="date"
                        placeholder="请选择入职日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="归属部门" prop="department">
                    <el-select v-model="form.departmentId" placeholder="请选择部门">
                        <el-option v-for="dept in departmentList" :key="dept.departmentId" :label="dept.departmentName"
                            :value="dept.departmentId"></el-option>
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
    name: "EmployeeManagement",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            searchName: '',
            searchGender: '',
            dateRange: "",
            total: 0,
            ids: [],
            fromVisible: false,
            form: {},
            departmentList: []
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
                this.$request.delete(`/employee/${this.ids}`).then(res => {

                    if (res.code === 200) {   // 表示操作成功
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
                this.$request.delete(`/employee/${id}`).then(res => {
                    console.log(88888888888888, this.res);
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
            this.fromVisible = true; // 打开新增员工的弹窗
            this.form = {}; // 清空表单数据
        },
        handleEdit(row) {
            this.form = JSON.parse(JSON.stringify(row)); // 深拷贝数据，避免直接修改表格中的数据影响到弹窗中的数据
            this.fromVisible = true; // 打开编辑员工的弹窗

        },
        load() {
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                name: this.searchName,
                begin: this.dateRange[0],
                end: this.dateRange[1],
            }
            this.$request.get(this.makeUrl(params)).then(res => {
                this.tableData = res.data.row
                this.total = res.data.total
            })
            this.$request.get('/departments').then(res => {
                //处理数据
                this.departmentList = res.data
                console.log(1111111111, this.departmentList);
            })

        },
        handleSelectionChange(rows) {
            this.ids = rows.map(v => v.employeeId)
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
        save() {
            this.$refs.employeeForm.validate((valid) => {
                console.log(7777777777777777, valid);
                if (valid) {
                    this.$request({
                        url: this.form.employeeId ? '/employee' : '/employee',
                        method: this.form.employeeId ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === 200) {
                            this.$message.success('保存成功');
                            this.fromVisible = false;
                            this.load(); // 重新加载员工列表
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
            if (params.name !== '') {
                paramsList.push(`&name=${encodeURIComponent(params.name)}`);
            }
            if (params.gender) {
                paramsList.push(`&gender=${encodeURIComponent(params.gender)}`);
            }
            if (params.begin) {
                paramsList.push(`&begin=${encodeURIComponent(params.begin)}`);
            }
            if (params.end) {
                paramsList.push(`&end=${encodeURIComponent(params.end)}`);
            }
            const url = `/employee?${paramsList.join("")}`;
            return url
        }
    }
}
</script>

<style scoped></style>