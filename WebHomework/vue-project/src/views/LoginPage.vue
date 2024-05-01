<template>
  <div class="page-container">
    <div class="login-form">
      <img src="../assets/login.png" alt style="width: 50%; flex: 1" />
      <div class="login-form-input">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="loginRef">
          <div class="login-slogan">欢迎登录教学管理系统</div>
          <el-form-item prop="userID">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入账号" v-model="user.userID"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码"
              v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">登 录</el-button>
          </el-form-item>
          <div style="display: flex">
            <div style="flex: 1">
              还没有账号？请
              <span style="color: #0f9876; cursor: pointer" @click="$router.push('/register')">注册</span>
            </div>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "LoginPage",
  components: {},
  data() {
    return {
      user: {
        userID: "",
        password: "",
      },
      rules: {
        userID: [
          { required: true, message: "请输入账号", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
        ],
      },
    };
  },
  created() { },
  methods: {
    login() {
      this.$refs.loginRef.validate((valid) => {
        if (valid) {
          // 假设你已经有了一个API调用服务$this.$request
          this.$request.post("/login", {
            userID: this.user.userID,
            password: this.user.password
          }).then((res) => {
            if (res.code === "200") {
              this.$router.push("/home");
              this.$message.success("登录成功");
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },
  },
};
</script>

<style>
.page-container {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #0f9876;
}

.login-form {
  display: flex;
  background-color: white;
  width: 50%;
  border-radius: 5px;
  overflow: hidden;
}

.login-form-input {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-slogan {
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
}
</style>
