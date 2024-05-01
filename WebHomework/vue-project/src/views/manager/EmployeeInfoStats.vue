<template>
    <div>
        <!-- 左边扇形统计图 -->
        <div style="width: 50%; display: inline-block;">
            <div id="genderChart" style="width: 100%; height: 400px;"></div>
            <div style="text-align: center; margin-top: 10px;">
                <p><strong>员工性别统计</strong></p>
                <p>男: 30人 (60%)</p>
                <p>女: 20人 (40%)</p>
            </div>
        </div>
        <!-- 右边条形统计图 -->
        <div style="width: 50%; display: inline-block;">
            <div id="positionChart" style="width: 100%; height: 400px;"></div>
            <div style="text-align: center; margin-top: 10px;">
                <p><strong>员工职位统计</strong></p>
                <p>班主任: 10人</p>
                <p>讲师: 20人</p>
                <p>学工主管: 15人</p>
                <p>教研主管: 25人</p>
            </div>
        </div>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    mounted() {
        this.renderCharts();
    },
    methods: {
        renderCharts() {
            // 模拟数据
            const genderData = [
                { name: '男', value: 30 },
                { name: '女', value: 20 },
            ];

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
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: genderData.map(item => {
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
                        data: [10, 20, 15, 25]
                    }
                ]
            };
            positionChart.setOption(positionOption);
        }
    }
};
</script>

<style></style>