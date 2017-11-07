var onBridgeReady = function () {

    $(document).trigger('bridgeready');

    if (!setForward()) {
        $(document).bind('weibachanged', function () {
            setForward();
        });
    }
};
if (document.addEventListener) {
    document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
} else if (document.attachEvent) {
    document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
    document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
}

function setForward() {
    if (1) {
        WeixinJSBridge.on('menu:share:appmessage', function (argv) {
            var $body = $('body'), appId = '',
                title = $body.attr('title'),
                imgUrl = $body.attr('icon'),
                link = $body.attr('link'),
                desc = $body.attr('desc');
            WeixinJSBridge.invoke('sendAppMessage', {
                //'appid': 'kczxs88',
                'img_url': imgUrl?imgUrl:undefined,
                'link': link,
                'desc': desc?desc:' ',
                'title': title
            }, function (res) {
                if (res && res['err_msg'] && res['err_msg'].indexOf('confirm') > -1) {
                    $(document).trigger('wx_sendmessage_confirm');
                }
            });
        });
        WeixinJSBridge.on('menu:share:timeline', function (argv) {
            $(document).trigger('wx_timeline_before');

            var $body = $('body'), appId = '',
                title = $body.attr('title'),
                imgUrl = $body.attr('icon'),
                link = $body.attr('link'),
                desc = $body.attr('desc');
            WeixinJSBridge.invoke('shareTimeline', {
                'img_url': imgUrl?imgUrl:undefined,
                'link': link,
                'desc': desc?desc:' ',
                'title': title
            }, function (res) {
                //貌似目前没有简报
            });
        });

        return true;
    }
    else {
        return false;
    }
}