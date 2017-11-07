<template>
  <div id="graph">
    <div class="title">
      <h1> Usuarios Influyentes</h1>
     
    </div>   
  <d3-network ref='net' v-bind:net-nodes="nodes" v-bind:net-links="links" v-bind:options="options" />
  </div>
</template>
<script>
import D3Network from "vue-d3-network";
//var D3Network = window.vueD3Network;
export default {
  name:"graph",
  components:{
    D3Network
  },
  data () {
    return{
      nodes: [
        { id: 1, name:  "dirvitcom", _color: 'orange'},
        { id: 2, name: "PhotonicSlime"},
        { id: 3, name: "Javi_fdez_7"},
        { id: 4, name: "romolocaponera"},
        { id: 5, name: "guajecc7"},
        { id: 6, name: "BboyKras"},
        { id: 7, name: "electronic_98"},
        { id: 8, name: "MissSanzFY"},
        { id: 9, name: "Bautizsta"}
      ],
      links: [
        { sid: 2, tid: 1 },
        { sid: 3, tid: 1 },
        { sid: 4, tid: 1 },
        { sid: 5, tid: 1 },
        { sid: 6, tid: 1 },
        { sid: 7, tid: 1 },
        { sid: 8, tid: 1 },
        { sid: 9, tid: 1 }
      ],
      nodeSize:54,
      canvas:false
    }
  },
  computed:{
    options (data) {
      return{
        force:3000,
        size:{ w:600, h:600 },
        nodeSize:this.nodeSize,
        nodeLabels:true,
        canvas:this.canvas
      }
    }
  },
  mounted(){
      this.options(this.data);

  }
}
</script>
<style src="vue-d3-network/dist/vue-d3-network.css"></style>
