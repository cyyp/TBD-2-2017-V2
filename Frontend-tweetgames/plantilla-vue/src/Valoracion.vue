
<template>
  <div class="svg-line-container" id="bCanvas"></div>
</template>
<script src="//d3js.org/d3.v3.min.js"></script>
<script>
import * as d3 from 'd3'
export default {
  name: 'barcharts',
  props: {

    data: {
      default: function () {
        return {
        }
      }
    }
  },
  data () {
    return {
    }
  },
  mounted () {
  console.log('Index.vue');
    // GET /someUrl
    this.$http.get('http://localhost:8080/restApi-tweetgame/games')
    .then(response=>{
       // get body data
      this.createLine('#bCanvas', response.data);
    }, response=>{
       console.log('error cargando datos');
    })
  },
  methods: {
    createLine(id, csv) {
      var canvasWidth = 400
      var canvasHeight = 200
      var margins = {top: 0, bottom: 10, left: 30, rigth: 10}
      var width = canvasWidth - margins.left - margins.rigth
      var height = canvasHeight - margins.top - margins.bottom

      // var width = barHeight * (csv.length - 1)
      var canvas = d3.select(id).append('svg')
       .attr("preserveAspectRatio", "xMinYMin meet")
        .attr("preserveAspectRatio", "xMinYMin meet")
        .attr("viewBox", "0 0 500 400")
        .classed("svg-content", true)
        .append("svg:g")
      var x = d3.scaleBand()
        .domain(d3.entries(csv).map(function (d) {return d.value.game_name}))
        .rangeRound([0, width]).padding(0.2)
      var y = d3.scaleLinear()
        .domain([0, d3.max(csv, function (d) {return d.resume.tweets_pos})])
        .range([height, 0])

      // Define the axes
      var xAx = d3.axisBottom(x).tickSize(0)
      var yAx = d3.axisLeft(y)

      canvas
        .append('g')
        .attr('class', 'spark-y axis')
        .attr("transform", "translate(" + margins.left + ",0)")
        .call(yAx)
        .selectAll("text")
        .attr("fill", "white")

      canvas
        .append('g')
        .attr('class', 'spark-x axis')
        .attr("transform", "translate(" + margins.left + "," + height + ")")
        .call(xAx)
         .selectAll("text")
            .style("text-anchor", "end")
            .attr("fill", "white")
            .attr("dx", "-.8em")
            .attr("dy", ".15em")
            .attr("transform", function(d) {
                return "rotate(-65)"
                });
       canvas.selectAll(".success").data(d3.entries(csv))
        .enter()
        .append("rect")
        .attr("transform", "translate(" + margins.left + ",0)")
        .attr('class', 'success')
        .attr("x", function (d) { return x(d.value.game_name) })
        .attr("width", x.bandwidth()/2)
        .attr("y", function (d) { return y(d.value.resume.tweets_pos) })
        .attr("height", function (d) { return height - y(d.value.resume.tweets_pos) })
        .style( "fill", "green")
      var bar = canvas.selectAll(".fail").data(d3.entries(csv))
        .enter()
        .append("rect")
        .attr("transform", "translate(" + margins.left + ",0)")
        .attr("class", "fail")
        .attr("x", function(d) { return x(d.value.game_name) + x.bandwidth()/2 })
        .attr("width", x.bandwidth()/2)
        .attr("y", function(d) { return y(d.value.resume.tweets_neg); })
        .attr("height", function(d) { return height - y(d.value.resume.tweets_neg); })
        .style( "fill", "red" )

      canvas
        .append("rect")
        .attr("y", 50)
        .attr("x", 430)
        .attr("transform", "translate(0,-8)")
        .attr("width", 10)
        .attr("height", 10)
        .style( "fill", "red")
      canvas
        .append("text")
        .attr("class", "spark-text")
        .attr("y", 50)
        .attr("x", 580)
        .text( "Positivo")
        .attr("fill", "white")
      canvas
        .append("text")
        .attr("class", "spark-text")
        .attr("y", 0.5)
        .attr("x", 130)
        .text("Valoración de juegos")
        .attr("fill", "white")
      canvas
        .append("rect")
        .attr("y", 50)
        .attr("x", 550)
        .attr("transform", "translate(0,-8)")
        .attr("width", 10)
        .attr("height", 10)
        .style( "fill", "green" )
      canvas
        .append("text")
        .attr("class", "spark-text")
        .attr("y", 50)
        .attr("x", 450)
        .text("Negativo")
        .attr("fill", "white")
      canvas
        .append("text")
        .attr("class", "spark-text")
        .attr("y", 150)
        .attr("x", 450)
        .text("Datos desde: 2017-10-25")
        .attr("fill", "white")
      canvas
        .append("text")
        .attr("class", "spark-text")
        .attr("y", 180)
        .attr("x", 450)
        .text("Datos hasta: 2017-11-07")
        .attr("fill", "white")
    }
  },

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.spark {
  stroke: steelblue;
  stroke-width: 1;
}
.svg-line-container {
    display: inline-block;
    position: relative;
    width: 70%;
    padding-bottom: 100%;
    vertical-align: top;
    overflow: visible;
}
.svg-content {
    display: inline-block;
    position: absolute;
    overflow: visible !important;
    top: 70;
    left: 10;
}
.axis {
    shape-rendering: crispEdges;
}
.spark-x line {
  stroke: lightgrey;
}
.spark-x .minor {
  stroke-opacity: .5;
}
.spark-x path {
  display: block;
}
.spark-y line, .spark-y path {
  fill: #999;

}
.axis path,
.axis line {
    fill: none;
    stroke: blue;
    stroke-width: 1;
    shape-rendering: crispEdges;
}
</style>
