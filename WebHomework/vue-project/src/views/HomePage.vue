<template>
    <div>
        <el-container>
            <!-- 侧边栏 -->
            <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #001529">
                <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
                    <!-- <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px"> -->
                    <span class="logo-title" v-show="!isCollapse">教学管理系统</span>
                </div>

                <el-menu :collapse="isCollapse" :collapse-transition="false" router background-color="#001529"
                    text-color="rgba(255, 255, 255, 0.65)" active-text-color="#fff" style="border: none"
                    :default-active="$route.path">
                    <!-- 系统首页 -->
                    <el-menu-item index="/home">
                        <i class="el-icon-s-home"></i>
                        <span slot="title">系统首页</span>
                    </el-menu-item>
                    <!-- 班级学员管理 -->
                    <el-submenu index="1">
                        <template slot="title">
                            <i class="el-icon-school"></i>
                            <span>班级学员管理</span>
                        </template>
                        <el-menu-item index="/class-management">班级管理</el-menu-item>
                        <el-menu-item index="/student-management">学员管理</el-menu-item>
                    </el-submenu>

                    <!-- 系统信息管理 -->
                    <el-submenu index="2">
                        <template slot="title">
                            <i class="el-icon-office-building"></i>
                            <span>系统信息管理</span>
                        </template>
                        <el-menu-item index="/department-management">部门管理</el-menu-item>
                        <el-menu-item index="/employee-management">员工管理</el-menu-item>
                    </el-submenu>

                    <!-- 数据统计管理 -->
                    <el-submenu index="3">
                        <template slot="title">
                            <i class="el-icon-pie-chart"></i>
                            <span>数据统计管理</span>
                        </template>
                        <el-menu-item index="/employee-info-statistics">员工信息统计</el-menu-item>
                        <el-menu-item index="/student-info-statistics">学员信息统计</el-menu-item>
                    </el-submenu>

                    <!-- 课程教务管理 -->
                    <el-submenu index="4">
                        <template slot="title">
                            <i class="el-icon-s-order"></i>
                            <span>课程教务管理</span>
                        </template>
                        <el-menu-item index="/course-management">课程管理</el-menu-item>
                        <el-menu-item index="/schedule-management">课程安排</el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <el-container>
                <!--头部区域-->
                <el-header>
                    <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
                    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
                        <el-breadcrumb-item>主页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{ $route.meta.name }}</el-breadcrumb-item>
                    </el-breadcrumb>

                    <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
                        <el-dropdown placement="bottom">
                            <div style="display: flex; align-items: center; cursor: default">
                                <img src="../assets/avatar.png" alt=""
                                    style="width: 40px; height: 40px; border-radius: 50%; margin: 0 5px">
                                <span>{{ user.name }}</span>
                            </div>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                </el-header>
                <!-- 主体区域 -->
                <el-main>
                    <router-view @update:user="updateUser" />
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>

export default {
    name: 'HomeView',
    data() {
        return {
            isCollapse: false,  // 不收缩
            asideWidth: '200px',
            collapseIcon: 'el-icon-s-fold',
            user: localStorage.getItem('local-user')
        }
    },
    mounted() {
        console.log(22222222, this.user);// 页面加载完成之后触发
        if (!this.user) {   // 当前的浏览器没有用户信息
            this.$router.push('/login')
        }
    },
    methods: {
        updateUser(user) {   // 获取子组件传过来的数据  更新当前页面的数据
            this.user = JSON.parse(JSON.stringify(user))  // 让父级的对象跟子级的对象毫无关联
        },
        logout() {
            localStorage.removeItem('local-user')  // 清除当前的token和用户数据
            this.$router.push('/login')
        },
        handleCollapse() {
            this.isCollapse = !this.isCollapse
            this.asideWidth = this.isCollapse ? '64px' : '200px'
            this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
        }
    }
}
</script>

<style>
.el-menu--inline {
    background-color: #000c17 !important;
}

.el-menu--inline .el-menu-item {
    background-color: #000c17 !important;
    padding-left: 49px !important;
}

.el-menu-item:hover,
.el-submenu__title:hover {
    color: #fff !important;
}

.el-submenu__title:hover i {
    color: #fff !important;
}

.el-menu-item:hover i {
    color: #fff !important;
}

.el-menu-item.is-active {
    background-color: #1890ff !important;
    border-radius: 5px !important;
    width: calc(100% - 8px);
    margin-left: 4px;
}

.el-menu-item.is-active i,
.el-menu-item.is-active .el-tooltip {
    margin-left: -4px;
}

.el-menu-item {
    height: 40px !important;
    line-height: 40px !important;
}

.el-submenu__title {
    height: 40px !important;
    line-height: 40px !important;
}

.el-submenu .el-menu-item {
    min-width: 0 !important;
}

.el-menu--inline .el-menu-item.is-active {
    padding-left: 45px !important;
}

/*.el-submenu__icon-arrow {*/
/*  margin-top: -5px;*/
/*}*/

.el-aside {
    transition: width .3s;
    box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
}

.logo-title {
    margin-left: 5px;
    font-size: 20px;
    transition: all .3s;
    /* 0.3s */
}

.el-header {
    box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
    display: flex;
    align-items: center;
}
</style>
