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
          <input id="url" type="text" class="form-control" value="http://petstore.swagger.io/v2/swagger.json"/>
          <span class="input-group-btn">
            <button id="go" class="btn btn-primary" data-toggle="modal" data-target="#myModal"
            type="button" v-on:click="say" >Go!</button>
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
          <tr v-for="(res,index) in resultats.urls">
            <td>{{ index+1 }}</td>
            <td>{{res.link}}</td>
            <td>{{res.reponseCode}}</td>
            <td>{{res.codes}}</td>
            <td>{{res.operationType}}</td>
          </tr>
        </tbody>
      </table>

    </div>

    <hr/>
  </template>

  <script>
  export default {
    name:'middle',
    data(){
      return {
        msg:"sss",
        resultats:""
      }
    },
    methods:{
      say:function()
      {
        this.resultats={"hostname":"petstore.swagger.io","urls":[{"codes":["405"],"valided":false,"reponseCode":"400","link":"http://petstore.swagger.io/v2/pet","operationType":"POST","parameters":{"body":"zwrWicBkQk"}},{"codes":["200","400"],"valided":true,"reponseCode":"200","link":"http://petstore.swagger.io/v2/pet/findByStatus","operationType":"GET","parameters":{}},{"codes":["200","400"],"valided":true,"reponseCode":"200","link":"http://petstore.swagger.io/v2/pet/findByTags","operationType":"GET","parameters":{}},{"codes":["200","400","404"],"valided":true,"reponseCode":"404","link":"http://petstore.swagger.io/v2/pet/NQmlCfzPdX","operationType":"GET","parameters":{}},{"codes":["405"],"valided":false,"reponseCode":"415","link":"http://petstore.swagger.io/v2/pet/Qbdpx5Ipqu","operationType":"POST","parameters":{"petId":"xDb9536kJd","name":"9IDK3G9Rcu","status":"LFtO5r0PDI"}},{"codes":["200"],"valided":false,"reponseCode":"415","link":"http://petstore.swagger.io/v2/pet/sj8QjiZQpU/uploadImage","operationType":"POST","parameters":{"file":"Dk6Gw9sQVm","petId":"0ao7jAQp93","additionalMetadata":"Q0rLKmtlgT"}},{"codes":["200"],"valided":true,"reponseCode":"200","link":"http://petstore.swagger.io/v2/store/inventory","operationType":"GET","parameters":{}},{"codes":["200","400"],"valided":true,"reponseCode":"400","link":"http://petstore.swagger.io/v2/store/order","operationType":"POST","parameters":{"body":"QnZW6ZaWoX"}},{"codes":["200","400","404"],"valided":true,"reponseCode":"404","link":"http://petstore.swagger.io/v2/store/order/e14gLXJu9q","operationType":"GET","parameters":{}},{"codes":["default"],"valided":false,"reponseCode":"400","link":"http://petstore.swagger.io/v2/user","operationType":"POST","parameters":{"body":"BmLuMSxbNE"}},{"codes":["default"],"valided":false,"reponseCode":"400","link":"http://petstore.swagger.io/v2/user/createWithArray","operationType":"POST","parameters":{"body":"ZKVl4VUwwR"}},{"codes":["default"],"valided":false,"reponseCode":"400","link":"http://petstore.swagger.io/v2/user/createWithList","operationType":"POST","parameters":{"body":"lFGvHoZe2t"}},{"codes":["200","400"],"valided":true,"reponseCode":"200","link":"http://petstore.swagger.io/v2/user/login","operationType":"GET","parameters":{}},{"codes":["default"],"valided":false,"reponseCode":"200","link":"http://petstore.swagger.io/v2/user/logout","operationType":"GET","parameters":{}},{"codes":["200","400","404"],"valided":true,"reponseCode":"404","link":"http://petstore.swagger.io/v2/user/9eA2aXJgW0","operationType":"GET","parameters":{}}]};
      }
    },
    created:function(){
    }
  }
  </script>

  <style lang="css">
  </style>
