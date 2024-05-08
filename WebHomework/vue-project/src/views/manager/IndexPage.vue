<template>
    <div>
        <div style="display: flex">
            <el-card style="width: 100%;font-size: 50px;">
                <span>Welcome!</span>
            </el-card>
        </div>
        <div
            style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px; font-size: 30px;">
            你好，管理员，祝你开心每一天！
        </div>
    </div>
</template>

<script>
export default {
    name: "IndexPage",
    data() {
        return {
            user: localStorage.getItem('local-user'),
            notices: [],
        }
    },
    created() {
        // if (this.user) {
        //     this.$request.get(`${this.user}`).then(res => {

        //     })
        // }
    },
    methods: {

    }
}
</script>