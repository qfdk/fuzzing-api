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
            <button id="go" class="btn btn-primary" type="button" v-on:click="say" >Go!</button>
          </span>
        </div>
      </div>
    </div>
    <br>
    <div class="jumbotron" style="background-color: #fff9f0;">
      <table class="table">
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
          <tr v-for="(res,index) in resultats">
            <td>{{ index+1 }}</td>
            <td>{{res.link}}</td>
            <td>{{res.reponseCode}}</td>
            <td>{{res.codes}}</td>
            <td>{{res.operationType}}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <spinner ref="spinner" :size="size" :fixed="fixed" text="I'm doing my work now... " style="display: none;"></spinner>
    <hr/>
  </template>

  <script>

  import spinner from 'vue-strap/src/spinner';

  export default {
    name:'middle',
    components:{
      spinner
    },
    data(){
      return {
        msg:"sss",
        url:"http://petstore.swagger.io/v2/swagger.json",
        resultats:"",
        fixed:true,
        size:'lg'
      }
    },
    methods:{
      say:function()
      {
        this.$refs.spinner.show()
        var tmp='http://'+window.location['hostname']+':8080/api/v1/getPath?url=';
        this.$http.get(tmp+this.url).then((result) => {
          console.log(result.body.urls);
          this.resultats=result.body.urls;
          this.$refs.spinner.hide()
        }, (error) => {

        })
      }
    },
    created:function(){
    }
  }
  </script>

  <style lang="css">
  </style>
