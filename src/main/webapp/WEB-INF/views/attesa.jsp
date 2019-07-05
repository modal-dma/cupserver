<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.js"></script>	
<meta charset="ISO-8859-1">
<title>Bar Chart</title>
</head>
<body>
<!-- 
<div class="chart-container" style="position: relative; height:80vh; width:90vw">
 -->
<canvas id="myChart"></canvas>
<!-- </div>  -->
<script>
$.ajax({
	    type: "GET",
		url: "http://localhost:8080/modal/api/1.0.0/attesaPerBranca",
		async: false,
		error: function(e) {
			error({'error': e});
		       //alert("Impossibile comunicare con il servizio DSS " + e.message);
		},
		success: function( response ) {		    		    
		    printChart(response);
		}
	});
	
	
function printChart(model)
{
	var color = Chart.helpers.color;
	
	
	var ctx = document.getElementById('myChart').getContext('2d');
	var myChart = new Chart(ctx, {
    	type: 'bar',
    	data: {
	        labels: model.labels,
	        datasets: [{
	            label: 'Min gg di attesa',
	            data: model.data[0],	            
	            backgroundColor: 'rgba(255, 99, 132, 1)',
	            /*
	            borderColor: [
	                'rgba(255, 99, 132, 1)',
	                'rgba(54, 162, 235, 1)',
	                'rgba(255, 206, 86, 1)',
	                'rgba(75, 192, 192, 1)',
	                'rgba(153, 102, 255, 1)',
	                'rgba(255, 159, 64, 1)'
	            ],*/
	            borderWidth: 1
	        }, 
	        {
	            label: 'Max gg di attesa',
	            data: model.data[1],
	            backgroundColor: 'rgba(54, 162, 235, 1)',	            
	            /*
	            borderColor: [
	                'rgba(255, 99, 132, 1)',
	                'rgba(54, 162, 235, 1)',
	                'rgba(255, 206, 86, 1)',
	                'rgba(75, 192, 192, 1)',
	                'rgba(153, 102, 255, 1)',
	                'rgba(255, 159, 64, 1)'
	            ],*/
	            borderWidth: 1
	        }, 
	        {
	            label: 'Media gg di attesa',
	            data: model.data[2],	            
	            backgroundColor: 'rgba(153, 102, 255, 1)',
	            /*
	            borderColor: [
	                'rgba(255, 99, 132, 1)',
	                'rgba(54, 162, 235, 1)',
	                'rgba(255, 206, 86, 1)',
	                'rgba(75, 192, 192, 1)',
	                'rgba(153, 102, 255, 1)',
	                'rgba(255, 159, 64, 1)'
	            ],*/
	            borderWidth: 1
	        }]
	    },
	    options: {
	    	aspectRatio: 4/3,
	    	responsive: true,
	        scales: {
	            yAxes: [{
	                ticks: {
	                    beginAtZero: true
	                }
	            }]
	        }
	    }
	});
	
	myChart.canvas.parentNode.style.height = '300px';
	//myChart.canvas.parentNode.style.width = '128px';
}

function RGB2Color(r,g,b, a)
{
  return 'rgba(' + 20 + ', ' + color + ', ' + color + ', 0.2)';
}

/*
var count = model.data.length;
var backgroundColorArray = [];

var frequency = .3;
var amplitude = 127;
var center = 128;
var phase = 128;
for(var i = 0; i < count; i++)
{
	red   = Math.sin(frequency*i+2+phase) * width + center;
    green = Math.sin(frequency*i+0+phase) * width + center;
    blue  = Math.sin(frequency*i+4+phase) * width + center;

    
	backgroundColorArray.push('rgba(' + 20 + ', ' + color + ', ' + color + ', 0.2)');
	
	   // Note that &#9608; is a unicode character that makes a solid block
	   document.write( '<font style="color:' + RGB2Color(v,v,v) + '">&#9608;</font>');
	}
	
	var color = 20 + i * 2;

}
*/
</script>

</body>
</html>