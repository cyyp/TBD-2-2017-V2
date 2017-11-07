<template>
<div class="w3-container" >

  <div class="w3-container w3-gray w3-cell m4 w3-cell-middle">
      <h1>Ranking de Juegos</h1>
      <center><p>Estadística de los juegos más comentados en Twitter entre 2017-10-25 y 2017-11-07</p></center>
    </div>

     <div class="w3-container w3-light-gray w3-cell m1">
      <div class="w3-container w3-gray w3-cell m4" id="pie">
        <div >
        <div id="chart"></div>
      </div>
      </div>
    </div>
</div>

</template>
<script>
import * as d3 from 'd3';
export default{
  data: function(){
    return {
      datos:[],
      fecha:[]
    }
  },
  methods:{

     loadGraph:function(data){

        var width = 1200;
        var height = 500;
        var radius = Math.min(width, height) / 2;
        var donutWidth = 20;
        var legendRectSize = 10;
        var legendSpacing = 10;
        var color = d3.scaleOrdinal(["#98abc5", "#c27dd3","#d13333","#7ec21a","#1abfc2","#e018b0","#e2ef1e","#1eef4a"]);
        var body = d3.select("#pie");
        var svg = body.select('#chart')
          .append('svg')
          .attr('width', width)
          .attr('height', height)
          .append('g')
          .attr('transform', 'translate(' + (width / 2) +
            ',' + (height / 2) + ')');
        var arc = d3.arc()
          .innerRadius(0)
          .outerRadius(radius - 18);
        var pie = d3.pie()
          .value(function(d) { return d.resume.total_tweets; })
          .sort(null);
        var path = svg.selectAll('path')
          .data(pie(data))
          .enter()
          .append('path')
          .attr('d', arc)
          .attr('fill', function(d, i) {
            return color(d.data.game_name);
          });

        var legend = svg.selectAll('.legend')
          .data(color.domain())
          .enter()
          .append('g')
          .attr('class', 'legend')
          .attr('transform', function(d, i) {
            var height = legendRectSize + legendSpacing;
            var offset =  height * color.domain().length / 2;
            var horz = 240;
            var vert = i * height - offset;
            return 'translate(' + horz + ',' + vert + ')';
          });
        legend.append('rect')
          .attr('width', legendRectSize)
          .attr('height', legendRectSize)
          .style('fill', color)
          .style('stroke', color);

        legend.append('text')
          .attr('x', legendRectSize + legendSpacing)
          .attr('y', legendRectSize - legendSpacing+10)
          .attr("fill", "white")
          .text(function(d) { return d; });

        legend.append('text')
          .attr('x', legendRectSize + legendSpacing+ 80)
          .attr('y', legendRectSize - legendSpacing)
          .attr("fill", "white")
     },
     fecha:function(){
      var legend = svg.selectAll('.legend')
          .data(color.domain())
          .enter()
          .append('g')
          .attr('class', 'legend')
          .attr('transform', function(d, i) {
            var height = legendRectSize + legendSpacing;
            var offset =  height * color.domain().length / 2;
            var horz = 240;
            var vert = i * height - offset;
            return 'translate(' + horz + ',' + vert + ')';
          });


        legend.append('text')
          .attr('x', legendRectSize + legendSpacing+ 80)
          .attr('y', legendRectSize - legendSpacing)
          .attr("fill", "white")
          .text("gththgjh")

    }

  },
  mounted(){
  this.$http.get('http://localhost:8080/restApi-tweetgame/games')
    .then(response=>{
      this.datos = response.body;
      console.log('as',this.datos[0].game_name);
      this.loadGraph(this.datos);
    }, response=>{
      console.log("error de conexion");
    })

  }
}
</script>
<style>
  .arc text {
    font: 10px sans-serif;
    text-anchor: middle;
  }
  .arc path {
    stroke: #fff;
  }


</style>
