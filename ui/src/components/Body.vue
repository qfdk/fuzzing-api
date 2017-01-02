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
      <p v-show="init_info" class="alert alert-info text-center">Please enter a <strong>swagger.json</strong> path then click go!</p>
      <accordion :one-at-atime="checked" :type="selected">
        <panel v-for="(res,index) in results" v-bind:type="[res.valided ? 'success':'danger']" >
          <h5 slot="header">
            <strong># {{ index+1 }}</strong> {{res.operationType}} {{res.link}}
          </h5>
          <table class="table table-condensed">
            <thead>
              <tr>
                <th>Type</th>
                <th>Waiting code</th>
                <th>Reponse code</th>
                <th>Parameters</th>
                <th>Parameter name & type</th>
                <th>Post json</th>
                <th>Reponse</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{{res.operationType}}</td>
                <td>{{res.codes}}</td> 
                <td>{{res.reponseCode}}</td>
                <td>{{res.parameters}}</td>
                <td>{{res.paramNameAndType}}</td>
                <td>{{res.postParam}}</td>
                <td>{{res.reponseBody}}</td>
              </tr>
            </tbody>
          </table>
        </panel>
      </accordion>
    </div>
    <spinner ref="spinner" :size="size" :fixed="fixed" text="I'm doing my work now... " style="display: none;"></spinner>
    <hr/>
    <alert v-model="showRight" placement="top-right" duration="5000" type="info" width="400px" dismissable>
      <span class="icon-ok-circled alert-icon-float-left"></span>
      <strong>Well Done!</strong>
      <p>You successfully read this important alert message :)</p>
    </alert>

    <alert v-model="showRightD" placement="top-right" duration="5000" type="danger" width="400px" dismissable>
      <span class="icon-ok-circled alert-icon-float-left"></span>
      <strong>Something was wrong!</strong>
      <p>Try again latter :(</p>
    </alert>

  </template>

  <script>


  import alert from 'vue-strap/src/Alert'
  import spinner from 'vue-strap/src/Spinner'
  import accordion from 'vue-strap/src/Accordion'
  import panel from 'vue-strap/src/Panel'

  export default {
    name:'middle',
    components:{
      spinner,
      accordion,
      panel,
      alert
    },
    data(){
      return {
        url:"http://petstore.swagger.io/v2/swagger.json",
        results:"",
        fixed:true,
        size:'lg',
        selected: 'info',
        checked:true,
        init_info:true,
        showRight:false,
        showRightD:false
      }
    },
    methods:{
      go:function()
      {
        this.$refs.spinner.show()
        this.init_info=false;
        this.showRightD=false;
        var tmp='http://'+window.location['hostname']+':8080/api/v1/getPath?url=';
        this.$http.get(tmp+this.url).then((result) => {
          this.results=result.body.urls;
          this.$refs.spinner.hide()
          this.showRight=true;
        }, (error) => {
          this.$refs.spinner.hide()
          this.showRightD=true;
          this.init_info=true;
        })
      }
    }
  }
  </script>

  <style lang="css">
  </style>
