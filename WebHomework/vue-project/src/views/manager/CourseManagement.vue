<template>
    <div>
        <div>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="课程名称">
                    <el-input v-model="searchName" placeholder="请输入课程名称"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search">查询</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" plain @click="handleAdd">新增课程</el-button>
            <el-button type="danger" plain @click="delBatch">批量删除</el-button>
        </div>

        <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
            @selection-change="handleSelectionChange" :row-key="getRowKeys">
            <el-table-column type="selection" width="55" align="center" reserve-selection></el-table-column>
            <el-table-column label="序号" align="center" type="index" :index="indexMethod"></el-table-column>
            <el-table-column prop="courseName" label="课程名称" align="center" sortable></el-table-column>
            <el-table-column prop="daysOfWeek" label="周中上课日" align="center">
                <template v-slot="{ row }">
                    {{ formatDaysOfWeek(row.daysOfWeek) }}
                </template>
            </el-table-column>
            <el-table-column prop="startTime" label="开始时间" align="center"></el-table-column>
            <el-table-column prop="endTime" label="结束时间" align="center"></el-table-column>
            <el-table-column label="操作" align="center" width="220px">
                <template v-slot="{ row }">
                    <el-button size="mini" type="primary" plain @click="handleEdit(row)">编辑</el-button>
                    <el-button size="mini" type="danger" plain @click="del(row.courseId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[5, 10, 20, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
        <el-dialog title="课程信息" :visible.sync="fromVisible">
            <el-form :model="form" label-width="80px" ref="courseForm">
                <el-form-item label="课程名称" prop="courseName">
                    <el-input v-model="form.courseName" placeholder="请输入课程名称"></el-input>
                </el-form-item>
                <el-form-item label="上课日" prop="daysOfWeek">
                    <el-select v-model="form.daysOfWeek" multiple placeholder="请选择上课日" :key="form.daysOfWeek">
                        <el-option label="周一" value="1"></el-option>
                        <el-option label="周二" value="2"></el-option>
                        <el-option label="周三" value="3"></el-option>
                        <el-option label="周四" value="4"></el-option>
                        <el-option label="周五" value="5"></el-option>
                        <el-option label="周六" value="6"></el-option>
                        <el-option label="周日" value="7"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="开始时间" prop="startTime">
                    <el-time-picker v-model="form.startTime" value-format="HH:mm:ss"
                        placeholder="选择开始时间"></el-time-picker>
                </el-form-item>
                <el-form-item label="结束时间" prop="endTime">
                    <el-time-picker v-model="form.endTime" value-format="HH:mm:ss"
                        placeholder="选择结束时间"></el-time-picker>
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
            total: 0,
            ids: [],
            fromVisible: false,
            form: {},
        }
    },
    created() {
        this.load()
    },
    methods: {
        formatDaysOfWeek(days) {
            if (!days) return '';
            const weekDaysMap = {
                '1': '周一',
                '2': '周二',
                '3': '周三',
                '4': '周四',
                '5': '周五',
                '6': '周六',
                '7': '周日'
            };
            return days.split(',')
                .map(day => weekDaysMap[day.trim()])
                .filter(Boolean)
                .join(', ');
        },
        handleSelectionChange(rows) {
            this.ids = rows.map(v => v.courseId)
        },
        search() {
            this.load()
        },
        delBatch() {
            if (!this.ids.length) {
                this.$message.warning('请选择数据')
                return
            }
            this.$confirm('您确认批量删除这些数据吗？', '确认删除', { type: "warning" }).then(() => {
                this.$request.delete(`/course/${this.ids}`).then(res => {

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
                this.$request.delete(`/course/${id}`).then(res => {
                    if (res.code === 200) {   // 表示操作成功
                        this.$message.success('操作成功')
                        this.load()
                    } else {
                        this.$message.error(res.msg)  // 弹出错误的信息
                    }
                }).catch(() => {
                    this.$message.error("这个课还在上呢!")
                })
            }).catch(() => { })
        },
        handleAdd() {
            this.fromVisible = true; // 打开新增班级的弹窗
            this.form = {}; // 清空表单数据
        },
        handleEdit(row) {
            this.form = JSON.parse(JSON.stringify(row)); // 深拷贝数据，避免直接修改表格中的数据影响到弹窗中的数据
            if (this.form.daysOfWeek) {
                this.form.daysOfWeek = this.form.daysOfWeek.split(',')
            }
            this.fromVisible = true; // 打开编辑课程的弹窗
        },
        load() {
            let params = {
                pageNum: this.pageNum,
                pageSize: this.pageSize,
                name: this.searchName,
            }
            this.$request.get(this.makeUrl(params)).then(res => {
                this.tableData = res.data.row
                this.total = res.data.total
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

            this.form.daysOfWeek = this.form.daysOfWeek.join(",")
            this.$refs.courseForm.validate((valid) => {
                if (valid) {
                    this.$request({
                        url: '/course',
                        method: this.form.courseId ? 'PUT' : 'POST',
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
                paramsList.push(`&courseName=${encodeURIComponent(params.name)}`);
            }
            const url = `/course?${paramsList.join("")}`;
            return url
        },
        indexMethod(index) {
            return index + 1 + this.pageSize * (this.pageNum - 1);
        }
    }
}
</script>

<style scoped></style>