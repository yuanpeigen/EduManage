<template>
    <div class="page-container"
        style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #669fef">
        <div class="register-form"
            style="display: flex; background-color: white; width: 50%; border-radius: 5px; overflow: hidden">
            <img src="@/assets/register.png" alt="" style="width: 50%; flex: 1">
            <div style="flex: 1; display: flex; align-items: center; justify-content: center">
                <el-form :model="user" style="width: 80%" :rules="rules" ref="registerRef">
                    <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px">注册
                    </div>
                    <el-form-item prop="userID">
                        <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号"
                            v-model="user.userID"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码"
                            v-model="user.password"></el-input>
                    </el-form-item>
                    <el-form-item prop="confirmPass">
                        <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请确认密码"
                            v-model="user.confirmPass"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="info" style="width: 100%" @click="register">注 册</el-button>
                    </el-form-item>
                    <div style="display: flex">
                        <div style="flex: 1">已经有账号了？请 <span style="color: #6e77f2; cursor: pointer"
                                @click="$router.push('/login')">登录</span></div>
                    </div>
                </el-form>
            </div>
        </div>

    </div>
</template>

<script>

export default {
    name: "RegisterPage",
    data() {
        // 密码校验
        const validatePassword = (rule, confirmPass, callback) => {
            if (confirmPass === '') {
                callback(new Error('请确认密码'))
            } else if (confirmPass !== this.user.password) {
                callback(new Error('两次输入的密码不一致'))
            } else {
                callback()
            }
        }
        return {
            user: {
                userID: '',
                password: '',
                confirmPass: ''
            },
            rules: {
                userID: [
                    { required: true, message: '请输入账号', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                ],
                confirmPass: [
                    { required: true, validator: validatePassword, trigger: 'blur' }
                ],
            }
        }
    },
    created() {

    },
    methods: {
        register() {
            this.$ref.registerRef.validate((valid) => {
                if (valid) {
                    // 假设你已经有了一个API调用服务$this.$request
                    this.$request.post('/register', {
                        userID: this.user.userID,
                        password: this.user.password
                    }).then(res => {
                        if (res.code === '200') {
                            this.$router.push('/login')
                            this.$message.success('注册成功')
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }
            })
        }
    }
}
</script>

<style scoped></style>