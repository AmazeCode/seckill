//存放主要交互逻辑js代码
// javascript 模块化
var seckill = {
    //封装秒杀相关ajax的url
    URL : {
        now : function () {
            return '/seckill/time/now';
        }
    },
    handleSeckillKill : function(seckillId,node){
        //获取秒杀地址，控制显示逻辑，执行秒杀
        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');//按钮
    },
    validatePhone : function(phone){
        if (phone && phone.length ==11 && !isNaN(phone)){
            return true;
        } else{
            return false;
        }
    },
    countdown:function(seckillId,nowTime,startTime,endTime){
        var seckillBox = $('#seckill-box');
        //时间判断
        if(nowTime > endTime){
            //秒杀结束
            seckillBox.html('秒杀结束');
        }else if (nowTime < startTime){
            //秒杀未开始,计时事件绑定
            var killTime = new Date(startTime + 1000);//防止用户计时时间偏移
            seckillBox.countdown(killTime,function (event) {
                //时间格式
                var format = event.startTime('秒杀计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                //时间完成后回调事件
            }).on('finish.countdown',function () {

                seckill.handleSeckillKill(seckillId,seckillBox);
            });
        } else{
            //秒杀开始
            seckill.handleSeckillKill(seckillId,seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail : {
        //详情也初始化
        init : function (params) {
            //用户手机验证和登陆，计时交互
            //规划我们的交互流程
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');

            //验证手机号
            if(!seckill.validatePhone(killPhone)){
                //绑定手机号
                //控制输出
                var killPhoneModel = $('#killPhoneModel');
                //显示弹出层
                killPhoneModel.modal({
                    show : true,//显示弹出层
                    backdrop : 'static',//禁止位置关闭
                    keyboard : false//关闭键盘事件
                })
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        //电话写入cookie
                        $.cookie( 'killPhone', inputPhone, {expires:7, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                    }
                });
            }
            //已经登陆
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']){
                    var nowTime = result['data'];
                    //时间判断,计时交互
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                } else{
                    console.log('result:'+result);
                }
            });
        }
    }
}