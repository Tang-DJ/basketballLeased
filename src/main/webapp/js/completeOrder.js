$(document).ready(function () {
    var orderDetailUrl = "http://localhost:8080/orderDetail.do";
    var completeOrderUrl = "http://localhost:8080/payment.do";

    var loc = location.href;
    var n1 = loc.length;//地址的总长度
    var n2 = loc.indexOf("=");//取得=号的位置
    var orderId = decodeURI(loc.substr(n2+1, n1-n2));//从=号后面的内容

    var userId;

    function init() {
        $.ajax({
            type: 'GET',
            url: orderDetailUrl,
            data: {
                orderId:orderId
            },
            success: function (data) {
                console.info(data);
                if(data.data=="0"){
                    var listData = data.orderDetail;
                    info(data.totalTime,listData,data.user,data.deposit);
                    userId = data.user;
                    $("#sub").removeAttr("disabled");
                }
                else{
                    alert(data.message);
                }
            },
            dataType: "json"
        });
    }

    function MillisecondToDate(msd) {
        var time = parseFloat(msd) /1000;
        if (null!= time &&""!= time) {
            if (time >60&& time <60*60) {
                time = parseInt(time /60.0) +"分钟"+ parseInt((parseFloat(time /60.0) -
                    parseInt(time /60.0)) *60) +"秒";
            }else if (time >=60*60&& time <60*60*24) {
                time = parseInt(time /3600.0) +"小时"+ parseInt((parseFloat(time /3600.0) -
                    parseInt(time /3600.0)) *60) +"分钟"+
                    parseInt((parseFloat((parseFloat(time /3600.0) - parseInt(time /3600.0)) *60) -
                        parseInt((parseFloat(time /3600.0) - parseInt(time /3600.0)) *60)) *60) +"秒";
            }else {
                time = parseInt(time) +"秒";
            }
        }else{
            time = "0 时 0 分0 秒";
        }
        return time;
    }


    function info(totalTime,listData,user,money) {

        listData.lendTime = new Date(listData.lendTime).toLocaleString();
        listData.returnTime = new Date(listData.returnTime).toLocaleString();
        totalTime = MillisecondToDate(totalTime);

        var temp = [];
        temp.push('<div class="col-md-12"><p class="col-md-3">订单编号:</p><span>'+ listData.orderID +'</span></div>');
        temp.push('<div class="col-md-12"><p class="col-md-3">篮球编号:</p><span>'+ listData.basketball.basketballID +'</span></div>');
        temp.push('<div class="col-md-12"><p class="col-md-3">篮球型号:</p><span>'+ listData.basketball.model +'</span></div>');
        temp.push('<div class="col-md-12"><p class="col-md-3">租借押金:</p><span>'+ listData.basketball.rent.deposit +'</span></div>');
        temp.push('<div class="col-md-12"><p class="col-md-3">小时租金:</p><span>'+ listData.basketball.rent.billing +'</span></div>');
        temp.push('<div class="col-md-12"><p class="col-md-3">租借时间:</p><span>'+ listData.lendTime +'</span></div>');
        temp.push('<div class="col-md-12"><p class="col-md-3">当前时间:</p><span>'+ listData.returnTime +'</span></div>');
        temp.push('<div class="col-md-12"><p class="col-md-3">租借计时:</p><span>'+ totalTime +'</span></div>');
        temp.push('<div class="col-md-12"><p class="col-md-3">订单金额:</p><span>'+ listData.castMoney +'</span></div>');
        $('#binfo').html(temp.join(''));

        var tempp = [];
        tempp.push('<div class="col-md-12"><p class="col-md-3">当前用户:</p><span>'+ user +'</span></div>');
        tempp.push('<div class="col-md-12"><p class="col-md-3">存款:</p><span>'+ money +'</span></div>');
        $('#pinfo').html(tempp.join(''));
    }



    $("#sub").click(function () {
        var pressure = $("#pressure").val();
        var yes = $("#yes").attr('checked');
        var no = $("#no").attr('checked');

        if (pressure == null || pressure == "" ) {
            alert("请输入当前篮球压力！");
            return;
        }
        if(yes==false&&no==false){
            alert("请选择篮球是否损坏！");
            return;
        }

        var isBad = 0;
        if(yes==true&&no==false){
            isBad = 0;
        }
        else {
            isBad = 1;
        }

        $.ajax({
            type: 'POST',
            url: completeOrderUrl,
            data: {
                user: userId,
                orderNumber: orderId,
                nowperssure: pressure,
                isbad: isBad
            },
            success: function (data) {
                console.info(data);
                if(data.data=="0"){
                    alert("归还成功！");
                    $(location).attr('href', 'information.html');
                }
                else{
                    alert(data.message);
                }
            },
            dataType: "json"
        });
    });

    init();

});