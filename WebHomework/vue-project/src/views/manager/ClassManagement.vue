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
            <el-table-column type="selection" width="55" align="center" reserve-selection></el-table-column>
            <el-table-column prop="className" label="班级名称" align="center"></el-table-column>
            <el-table-column prop="classroom" label="班级教室" align="center"></el-table-column>
            <el-table-column prop="startTime" label="开班时间" align="center"></el-table-column>
            <el-table-column prop="graduationTime" label="毕业时间" align="center"></el-table-column>
            <el-table-column prop="headTeacher" label="班主任" align="center"></el-table-column>
            <el-table-column label="操作" align="center" width="220px">
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
        <!-- 编辑班级信息 -->
        <el-dialog title="班级信息" :visible.sync="fromVisible" width="30%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px" ref="formRef">
                <el-form-item label="班级名称" prop="className">
                    <el-input v-model="form.className" placeholder="班级名称"></el-input>
                </el-form-item>
                <el-form-item label="班级教室" prop="classroom">
                    <el-input v-model="form.classroom" placeholder="班级教室"></el-input>
                </el-form-item>
                <el-form-item label="开班时间" prop="startTime">
                    <el-date-picker v-model="form.startTime" type="datetime" placeholder="开班时间"></el-date-picker>
                </el-form-item>
                <el-form-item label="毕业时间" prop="graduationTime">
                    <el-date-picker v-model="form.graduationTime" type="datetime" placeholder="毕业时间"></el-date-picker>
                </el-form-item>
                <el-form-item label="班主任" prop="headTeacher">
                    <el-input v-model="form.headTeacher" placeholder="班主任"></el-input>
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
    name: "ClassManagement",
    data() {
        return {
            tableData: [//假数据
                { id: 1, className: '一班', classroom: '101', startTime: '2024-09-01', graduationTime: '2028-06-30', headTeacher: '张老师' },
                { id: 2, className: '二班', classroom: '102', startTime: '2024-09-01', graduationTime: '2028-06-30', headTeacher: '李老师' }
            ],
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
        this.load();
    },
    methods: {
        search() {
            this.load();
        },
        handleAdd() {
            this.fromVisible = true;
            this.form = {};
        },
        handleEdit(row) {
            this.form = Object.assign({}, row);
            this.fromVisible = true;
        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.$request({
                        url: this.form.id ? '/class/update' : '/class/add',
                        method: this.form.id ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {  // 表示成功保存
                            this.$message.success('保存成功')
                            this.fromVisible = false
                            this.load()
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }
            })
        },
        del(id) {
            this.$confirm('您确认删除吗？', '确认删除', { type: "warning" }).then(() => {
                // 发送删除请求
                // 假设删除成功后，刷新数据列表
                // 示例代码，实际需要根据接口情况修改
                this.$request.delete(`/class/delete/${id}`).then(res => {
                    if (res.code === '200') {   // 表示操作成功
                        this.$message.success('删除成功');
                        this.load(); // 删除成功后刷新数据列表
                    } else {
                        this.$message.error(res.msg);  // 弹出错误的信息
                    }
                });
            }).catch(() => { });
        },
        handleSelectionChange(rows) {
            this.ids = rows.map(v => v.id);
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
            return row.id;
        },
        load(pageNum) {  // 分页查询
            if (pageNum) this.pageNum = pageNum
            this.$request.get('/class/selectByPage', {
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
    }
}
</script>

<style scoped></style>