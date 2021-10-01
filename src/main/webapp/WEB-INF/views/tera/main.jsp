<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jsp"%>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>

<style>
.highcharts-figure, .highcharts-data-table table {
    min-width: 360px; 
    max-width: 800px;
    margin: 1em auto;
}

.highcharts-data-table table {
	font-family: Verdana, sans-serif;
	border-collapse: collapse;
	border: 1px solid #EBEBEB;
	margin: 10px auto;
	text-align: center;
	width: 100%;
	max-width: 500px;
}
.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}
.highcharts-data-table th {
	font-weight: 600;
    padding: 0.5em;
}
.highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
    padding: 0.5em;
}
.highcharts-data-table thead tr, .highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}
.highcharts-data-table tr:hover {
    background: #f1f7ff;
}

</style>

<title>insert</title>

</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jsp"%>
	<section>
		<br/><br/>
		<article class="row justify-content-center">
			<div class="col-sm-6 col-xl-3">
				<div>
				<h3  class="display-4 d-block l-h-n m-0 fw-500">${totalUserCnt }</h3>
				 <small class="m-0 l-h-n">No Of Users In DB</small>
				</div>
			</div>
			<div class="col-sm-6 col-xl-3">
				<div>
				 <h3  class="display-4 d-block l-h-n m-0 fw-500">${totalMusicChCnt }</h3>
				 <small class="m-0 l-h-n">No Of MusicChs In DB</small> 
				</div>
			</div>
		</article>
		<br/>
		
		<article class="">
			<figure class="highcharts-figure">
			    <div id="container"></div>
			</figure>
			<br/>
			<figure class="highcharts-figure">
			    <div id="container2"></div>
			</figure>
		</article>
		
	</section>
</body>

<%@ include file="/WEB-INF/views/include/include-footer.jsp"%>

<script type="text/javascript">
<%--json으로 묶으면 차트를 그릴때 문제가 생긴다--%>
<%-- 차트를 그릴때 필요한 합계 데이터.--%>
var arrData = [];
<%-- 차트를 그릴때 필요한 년도 데이터.--%>
var arrCategory = [];

<c:forEach var="result" items="${visitedList }" varStatus="status">
  arrData.unshift(+'${result.cnt}');
  arrCategory.unshift('${result.idate}');
</c:forEach>

Highcharts.chart('container', {
  chart: {
    zoomType: 'x'
  },
  title: {
    text: 'Daily User Visited'
  },
  subtitle: {
    text: document.ontouchstart === undefined ?
      'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
  },
  xAxis: {
    type: 'datetime'
    ,categories:arrCategory
  },
  yAxis: {
    title: {
      text: 'Number of Users Visited'
    }
  },
  legend: {
    enabled: false
  },
  plotOptions: {
    area: {
      fillColor: {
        linearGradient: {
          x1: 0,
          y1: 0,
          x2: 0,
          y2: 1
        },
        stops: [
          [0, Highcharts.getOptions().colors[0]],
          [1, Highcharts.color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
        ]
      },
      marker: {
        radius: 2
      },
      lineWidth: 1,
      states: {
        hover: {
          lineWidth: 1
        }
      },
      threshold: null
    }
  },

  series: [{
    type: 'area',
    name: 'USD to EUR',
    data: arrData
  }]
});

var videoFolderSize=+"${videoFolderSize}"
var maxFolderSize=1073741824
Highcharts.chart('container2', {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie'
    },
    title: {
        text: 'Server Disk Usage'
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    accessibility: {
        point: {
            valueSuffix: '%'
        }
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
            }
        }
    },
    series: [{
        name: 'Brands',
        colorByPoint: true,
        data: [{
            name: 'Free Disk Space',
            y: ((maxFolderSize-videoFolderSize)*100)/maxFolderSize,
            sliced: true,
            selected: true
        }, {
            name: 'Used Disk Space',
            y: (videoFolderSize*100)/maxFolderSize
        }]
    }]
});
</script>
</html>