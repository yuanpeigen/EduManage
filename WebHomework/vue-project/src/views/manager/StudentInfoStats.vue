<template>
    <div>
        <!-- 左边扇形统计图 -->
        <div style="width: 50%; display: inline-block;">
            <div id="genderChart" style="width: 100%; height: 400px;"></div>
            <div style="text-align: center; margin-top: 10px;">
                <p><strong>学生性别统计</strong></p>
            </div>
        </div>
        <!-- 右边条形统计图 -->
        <div style="width: 50%; display: inline-block;">
            <div id="educationChart" style="width: 100%; height: 400px;"></div>
            <div style="text-align: center; margin-top: 10px;">
                <p><strong>学生最高学历统计</strong></p>
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
            const educationChart = echarts.init(document.getElementById('educationChart'));
            const educationOption = {
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
                        data: ['本科', '硕士', '博士'],
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
                        data: [60, 30, 10]
                    }
                ]
            };
            educationChart.setOption(educationOption);
        }
    }
};
</script>

<style></style>