<template>
    <div>
        <div>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="班级名称">
                    <el-input v-model="searchName" placeholder="请输入班级名称"></el-input>
                </el-form-item>
                <el-form-item label="结课时间">
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
            <el-button type="primary" plain @click="handleAdd">新增班级</el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            :row-key="getRowKeys">
            <el-table-column label="序号" align="center" type="index" :index="indexMethod"></el-table-column>
            <el-table-column prop="className" label="班级名称" align="center" sortable></el-table-column>
            <el-table-column prop="classroom" label="班级教室" align="center"></el-table-column>
            <el-table-column prop="startTime" label="开班时间" align="center"></el-table-column>
            <el-table-column prop="finishTime" label="毕业时间" align="center"></el-table-column>
            <el-table-column prop="classmasterId" label="班主任" align="center">
                <template slot-scope="scope">
                    {{ getTeacherName(scope.row.classmasterId) }}
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center" width="220px">
                <template v-slot="{ row }">
                    <el-button size="mini" type="primary" plain @click="handleEdit(row)">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="del(row.classId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
        <el-dialog title="班级" :visible.sync="fromVisible">
            <el-form :model="form" label-width="80px" ref="classForm">
                <el-form-item label="班级名称" prop="className">
                    <el-input v-model="form.className" placeholder="请输入班级名称"></el-input>
                </el-form-item>
                <el-form-item label="班级教室" prop="classroom">
                    <el-input v-model="form.classroom" placeholder="请输入教室"></el-input>
                </el-form-item>
                <el-form-item label="开班时间" prop="startTime">
                    <el-date-picker :value="form.startTime" v-model="form.startTime" type="date"
                        placeholder="请选择开班时间"></el-date-picker>
                </el-form-item>
                <el-form-item label="毕业时间" prop="finishTime">
                    <el-date-picker :value="form.finishTime" v-model="form.finishTime" type="date"
                        placeholder="请选择毕业时间"></el-date-picker>
                </el-form-item>
                <el-form-item label="班主任" prop="classmasterId">
                    <el-select v-model="form.classmasterId" placeholder="请选择班主任">
                        <el-option v-for="classmaster in classmasterList" :key="classmaster.employeeId"
                            :label="classmaster.name" :value="classmaster.employeeId"></el-option>
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
    name: "ClassManagement",
    data() {
        return {
            tableData: [],  // 所有的数据
            pageNum: 1,   // 当前的页码
            pageSize: 5,  // 每页显示的个数
            searchName: '',
            dateRange: "",
            total: 0,
            ids: [],
            fromVisible: false,
            form: {},
            classmasterList: []
        }
    },
    created() {
        this.load()
    },
    methods: {
        search() {
            this.load()
        },
        del(id) {
            this.$confirm('您确认删除吗？', '确认删除', { type: "warning" }).then(() => {
                this.$request.delete(`/class/${id}`).then(res => {
                    if (res.code === 200) {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                }).catch(() => {
                    this.$message.error("这个班还有学生呢!")
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
        },
        load() {
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                name: this.searchName,
                begin: this.dateRange[0],
                end: this.dateRange[1],
            }
            console.log(44444444444444, params);
            this.$request.get(this.makeUrl(params)).then(res => {
                this.tableData = res.data.row
                this.total = res.data.total
                //请求班主任
                this.$request.get("/employee/classmaster").then(res => {
                    this.classmasterList = res.data
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
        save() {
            this.$refs.classForm.validate((valid) => {
                if (valid) {
                    this.$request({
                        url: '/class',
                        method: this.form.classId ? 'PUT' : 'POST',
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
                paramsList.push(`&className=${encodeURIComponent(params.name)}`);
            }
            if (params.begin) {
                paramsList.push(`&begin=${encodeURIComponent(params.begin)}`);
            }
            if (params.end) {
                paramsList.push(`&end=${encodeURIComponent(params.end)}`);
            }
            const url = `/class?${paramsList.join("")}`;
            return url
        },
        getTeacherName(id) {
            const foundElement = this.classmasterList.find(item => item.employeeId === id);
            return foundElement.name;
        },
        indexMethod(index) {
            return index + 1 + this.pageSize * (this.pageNum - 1);
        }
    }
}
</script>

<style scoped></style>