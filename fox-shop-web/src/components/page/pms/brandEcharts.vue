<template>
  <div>
    <div ref="main" style="width: 900px;height:400px;"></div>
  </div>
</template>

<script>
import {productCount} from "@/api/product";

export default {
  name: "brandEcharts",
  data() {
    return {
      option: {
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: {
          data: [],
          type: 'bar'
        }
      }
    }
  },

  methods: {
    /*初始化echarts*/
    drawLine() {
      productCount().then(result => {
        let data = result.data.data;
        for (let i = 0; i < data.length; i++) {
          this.option.series.data.push(data[i].count)

          if (data[i].beandName==null || data[i].beandName == ""){
            this.option.xAxis.data.push("未知品牌")
          }else{
            this.option.xAxis.data.push(data[i].beandName)
          }

        }
      /*初始化*/
        let myChart = this.$echarts.init(this.$refs.main)
        myChart.setOption(this.option);
      })

    },
  },

  mounted() {
    this.drawLine();
  }
}
</script>

<style scoped>

</style>