<template>
    <div style="display: flex">
        <!-- 左边扇形统计图 -->
        <div style="width: 50%; display: inline-block;">
            <div id="genderChart" style="width: 100%; height: 400px;"></div>
            <div style="text-align: center; margin-top: 10px;">
                <p style="font-size: 40px"><strong>员工性别统计</strong></p>
                <p style="font-size: 25px">{{ genderData.length ? genderData[0].name + ": " + genderData[0].value + "人" :
                    ""
                    }}
                </p>
                <p style="font-size: 25px">{{ genderData.length ? genderData[1].name + ": " + genderData[1].value + "人" :
                    ""
                    }}
                </p>
            </div>
        </div>
        <!-- 右边条形统计图 -->
        <div style="width: 50%; display: inline-block;">
            <div id="positionChart" style="width: 100%; height: 400px;"></div>
            <div style="text-align: center; margin-top: 10px;">
                <p style="font-size: 40px;"><strong>员工职位统计</strong></p>
                <p style="font-size: 21px">{{ numOfJobsArray ? "班主任" + ": " + numOfJobsArray[0] + "人" : "" }}</p>
                <p style="font-size: 21px">{{ numOfJobsArray ? "讲师" + ": " + numOfJobsArray[1] + "人" : "" }}</p>
                <p style="font-size: 21px">{{ numOfJobsArray ? "学工主管" + ": " + numOfJobsArray[2] + "人" : "" }}
                </p>
                <p style="font-size: 21px">{{ numOfJobsArray ? "教研主管" + ": " + numOfJobsArray[3] + "人" : "" }}
                </p>
            </div>
        </div>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: 'EmployeeInfoChart',
    data() {
        return {
            pieChartInstance: null,
            barChartInstance: null,
            pieChartOptions: {},
            barChartOptions: {},
            employeeList: [],
            genderData: [],
            numOfJobsArray: []
        };
    },
    mounted() {
        this.renderCharts();
    },
    methods: {
        renderCharts() {
            //请求数据
            this.$request.get("/employee/all").then(res => {
                this.employeeList = res.data
                console.log(222222222222, this.employeeList);
                // 填充数据
                this.genderData = [
                    { name: '男', value: this.employeeList.filter(item => item.gender === 1).length },
                    { name: '女', value: this.employeeList.filter(item => item.gender === 2).length },
                ];
                const jobs = [1, 2, 3, 4]
                this.numOfJobsArray = jobs.map(job =>
                    this.employeeList.filter(employee => employee.job === job).length
                );

                // 左边扇形统计图
                const genderChart = echarts.init(document.getElementById('genderChart'));
                const genderOption = {
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b}: {c} ({d}%)'
                    },
                    series: [
                        {
                            name: '性别统计',
                            type: 'pie',
                            radius: ['38%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                label: {
                                    show: true,
                                    fontSize: '30',
                                    fontWeight: 'bolder'
                                }
                            },
                            labelLine: {
                                show: false
                            },
                            data: this.genderData.map(item => {
                                return { value: item.value, name: item.name };
                            })
                        }
                    ]
                };
                genderChart.setOption(genderOption);

                // 右边条形统计图
                const positionChart = echarts.init(document.getElementById('positionChart'));
                const positionOption = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            data: ['班主任', '讲师', '学工主管', '教研主管'],
                            axisTick: {
                                alignWithLabel: true
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '人数',
                            type: 'bar',
                            barWidth: '60%',
                            data: this.numOfJobsArray
                        }
                    ]
                };
                positionChart.setOption(positionOption);
            })






        }
    }
};
</script>

<style></style>