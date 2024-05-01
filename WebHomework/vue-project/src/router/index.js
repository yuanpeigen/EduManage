import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HomePage',
    component: () => import('../views/HomePage.vue'),
    redirect: '/home',  // 重定向到主页
    children: [
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/IndexPage') },
      { path: 'class-management', name: 'ClassManagement', meta: { name: '班级管理' }, component: () => import('../views/manager/ClassManagement') },
      { path: 'student-management', name: 'StudentManagement', meta: { name: '学员管理' }, component: () => import('../views/manager/StudentManagement') },
      { path: 'department-management', name: 'DepartmentManagement', meta: { name: '部门管理' }, component: () => import('../views/manager/DepartmentManagement') },
      { path: 'employee-management', name: 'EmployeeManagement', meta: { name: '员工管理' }, component: () => import('../views/manager/EmployeeManagement') },
      { path: 'employee-info-statistics', name: 'EmployeeInfoStats', meta: { name: '员工信息统计' }, component: () => import('../views/manager/EmployeeInfoStats') },
      { path: 'student-info-statistics', name: 'StudentInfoStats', meta: { name: '学员信息统计' }, component: () => import('../views/manager/StudentInfoStats') },
      { path: 'course-management', name: 'CourseManagement', meta: { name: '课程管理' }, component: () => import('../views/manager/CourseManagement') },
      { path: 'schedule-management', name: 'ScheduleManagement', meta: { name: '课程安排' }, component: () => import('../views/manager/ScheduleManagement') },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginPage.vue'),

  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/RegisterPage.vue'),
  },
]

const router = new VueRouter({
  routes
})

export default router
