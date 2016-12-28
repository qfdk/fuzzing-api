<template lang="html">
  <div class="bot">
    <p>
      <a href="#" target="_blank">About</a> &middot; Code licensed under <a
          href="http://www.apache.org/licenses/LICENSE-2.0"
          target="_blank">Apache
      License v2.0</a>, documentation under <a href="http://creativecommons.org/licenses/by/4.0/" target="_blank">CC
      BY 4.0</a>.
  </p>
  <!-- Modal -->
  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                          aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title" id="myModalLabel">Information :)</h4>
              </div>
              <div class="modal-body">
                  <div class="alert alert-info" role="alert">Loading ...</div>
              </div>
          </div>
      </div>
  </div>
  </div>
</template>

<script>
export default {
  name:'footer'
}
</script>

<style lang="css">
</style>
