<template>
<div class="wrapper-user-popover">
    <div class="wrapper-action-list" transition="fade">
		<div class="item-userlogin">@{{ userLogin }}</div>
		<div class="item-edituser"><a href="#user/update">编辑资料</a></div>
        
        <a href="javascript:void(0);" @click="$emit('logout')">Logout</a>
    </div>
</div>
</template>

<script>
export default {
  	name: "popover-user",
  	props: ['userLogin'],
	data: function () {
    	return {
        	
    	}
	},

	methods: {
         
    }
}
</script>

<style scoped lang="stylus">
mainColor = #ddd

.wrapper-user-popover
    position relative

.wrapper-action-list 
    position absolute
    border-radius 3px
    width 200px
    background #fff
    padding 10px 20px
    box-shadow 0 6px 6px rgba(16, 16, 16, 0.04), 0 6px 6px rgba(0, 0, 0, 0.05)
    z-index 999
	.item-userlogin
		margin-bottom 12px
		font-size 18px
		
</style>