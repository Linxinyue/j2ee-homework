// 路径配置
var myDate = new Date();

if (myDate.getDay() == 0){
  var days = new Array("周一","周二","周三","周四","周五","周六","周日");
}else if(myDate.getDay() == 6){
  var days = new Array("周日","周一","周二","周三","周四","周五","周六");
}else if(myDate.getDay() == 5){
  var days = new Array("周六","周日","周一","周二","周三","周四","周五");
}else if(myDate.getDay() == 4){
  var days = new Array("周五","周六","周日","周一","周二","周三","周四");
}else if(myDate.getDay() == 3){
  var days = new Array("周四","周五","周六","周日","周一","周二","周三");
}else if(myDate.getDay() == 2){
  var days = new Array("周三","周四","周五","周六","周日","周一","周二");
}else if(myDate.getDay() == 1){
  var days = new Array("周二","周三","周四","周五","周六","周日","周一");
}

require.config({
  paths: {
    echarts: 'http://echarts.baidu.com/build/dist'
  }
});
// 使用
require(
  [
    'echarts',
    'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
  ],
  function(ec) {
    // 基于准备好的dom，初始化echarts图表
    var myChart = ec.init(document.getElementById('week-counts'), "macarons");

    var option = {
      title: {
        text: '最近一周访问量',
        subtext: '部分属实'
      },
      tooltip: {
        trigger: 'axis'
      },
      calculable: true,
      xAxis: [{
        type: 'category',
        boundaryGap: false,
        data: [days[0], days[1], days[2], days[3], days[4], days[5], days[6]]
      }],
      yAxis: [{
        type: 'value',
      }],
      series: [{
        name: '访问量',
        type: 'line',
        data: [myCounts[6], myCounts[5], myCounts[4], myCounts[3], myCounts[2], myCounts[1], myCounts[0]],
        markPoint: {
          data: [{
            type: 'max',
            name: '最大值'
          }, {
            type: 'min',
            name: '最小值'
          }]
        },
        markLine: {
          data: [{
            type: 'average',
            name: '平均值'
          }]
        }
      }]
    };


    // 为echarts对象加载数据
    myChart.setOption(option);
  }
);


require(
  [
    'echarts',
    'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
  ],
  function(ec) {
    // 基于准备好的dom，初始化echarts图表
    var myChart = ec.init(document.getElementById('class-counts'),"macarons");

    var option = {
      title: {
        text: '本周分类阅读量',
        subtext: '纯属虚构'
      },
      tooltip: {
        trigger: 'axis'
      },
      calculable: true,
      xAxis: [{
        type: 'category',
        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
      }],
      yAxis: [{
        type: 'value'
      }],
      series: [{
        name: '阅读量',
        type: 'bar',
        data: [1356, 1349, 1710, 2232, 1256, 1767, 1356, 1622, 2326, 3200, 1264, 2333],
        markPoint: {
          data: [{
            type: 'max',
            name: '最大值'
          }, {
            type: 'min',
            name: '最小值'
          }]
        },
        markLine: {
          data: [{
            type: 'average',
            name: '平均值'
          }]
        }
      }]
    };

    // 为echarts对象加载数据
    myChart.setOption(option);
  }
);

