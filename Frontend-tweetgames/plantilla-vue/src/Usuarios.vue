<template>
  <div class="center">
    <h2>{{title}}</h2>
    <a href="#/Nodos"><p>(NODOS)</p></a>
    <ul class="user-list">
      <li v-for="u,i in users">
     <img  width="60" height="50"  :src="u.user_profile_image_url"/>
        <a v-bind:href="u.user_url">{{u.user_name}}</a>

      </li>
    </ul>
  </div>
</template>
<script>
export default {
  data(){
    return{
      title:'Usuarios más Influyentes',
      users:[]
    }
  },
  mounted:function(){
    console.log('Index.vue');
    // GET /someUrl
    this.$http.get('http://localhost:8080/restApi-tweetgame/top')
    .then(response=>{
       // get body data
      this.users = response.body;
     console.log('users',this.users)
    }, response=>{
       // error callback
       console.log('error cargando lista');
    })
  }
}
</script>
