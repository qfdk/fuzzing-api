<template lang="html">

  <div class="container">

    <div class="jumbotron">
      <div class="row">
        <h1>Fuzzing or Amazing</h1>
      </div>
    </div>
    <div class="row">
      <div class="col-md-8 col-md-offset-2">
        <div class="input-group">
          <input id="url" type="text" v-model="url" class="form-control"/>
          <span class="input-group-btn">
            <button id="go" class="btn btn-primary" type="button" v-on:click="go" >Go!</button>
          </span>
        </div>
      </div>
    </div>
    <br>
    <div class="jumbotron" style="background-color: #fff9f0;">
      <!-- <table class="table">
      <thead>
      <tr>
      <th>#No.</th>
      <th>Target url</th>
      <th>Reponse code</th>
      <th>Result list</th>
      <th>Operation Type</th>
    </tr>
  </thead>

  <tbody>
  <tr v-for="(res,index) in results" v-bind:class="[res.valided ? 'success':'danger']">
  <td>{{index + 1}}</td>
  <td>{{res.link}}</td>
  <td>{{res.reponseCode}}</td>
  <td>{{res.codes}}</td>
  <td>{{res.operationType}}</td>
</tr>
</tbody>

</table> -->

<accordion :one-at-atime="checked" :type="selected">
  <panel v-for="(res,index) in results" v-bind:type="[res.valided ? 'success':'danger']" >
    <h5 slot="header">
    <strong># {{ index+1 }}</strong> {{res.operationType}} {{res.link}}
  </h5>
    <table class="table table-condensed">
      <thead>
        <tr>
          <th>Type</th> <th>reponse code</th><th>Reponse code</th><th>Parameters</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>{{res.operationType}}</td><td>{{res.codes}}</td> <td>{{res.reponseCode}}</td><td>{{res.parameters}}</td>
        </tr>
      </tbody>
    </table>
  </panel>
</accordion>
</div>
<spinner ref="spinner" :size="size" :fixed="fixed" text="I'm doing my work now... " style="display: none;"></spinner>
<hr/>
</template>

<script>


import spinner from 'vue-strap/src/spinner'
import accordion from 'vue-strap/src/accordion'
import panel from 'vue-strap/src/Panel'

export default {
  name:'middle',
  components:{
    spinner,
    accordion,
    panel
  },
  data(){
    return {
      url:"http://petstore.swagger.io/v2/swagger.json",
      results:"",
      fixed:true,
      size:'lg',
      selected: 'info'
    }
  },
  methods:{
    go:function()
    {
      this.$refs.spinner.show()
      var tmp='http://'+window.location['hostname']+':8080/api/v1/getPath?url=';
      this.$http.get(tmp+this.url).then((result) => {
        this.results=result.body.urls;
        this.$refs.spinner.hide()
      }, (error) => {
      })
    }
  }
}
</script>

<style lang="css">
</style>
