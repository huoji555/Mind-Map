(function($,touch){
    var y,height,c_h,position;
    function init_pageH(){
            var fn_h = function() {
                if(document.compatMode == "BackCompat")
                    var Node = document.body;
                else
                    var Node = document.documentElement;
                 return Math.max(Node.scrollHeight,Node.clientHeight);
            }
            var page_h = fn_h();
            var m_h = $(selector).height();
            page_h >= m_h ? c_h = page_h : c_h = m_h ;
            $(selector).css('zIndex',-1).first().css('zIndex',2);
    }
    function start(e){
        y = e.changedTouches[0].pageY;
        if($(this).hasClass('BMap_mask')){return;}
        var this_obj = $(this).closest(selector);
        var this_index = $(this_obj).index();
    }

    function move(e){
        e.preventDefault();
        e.stopPropagation();
        if($(this).hasClass('BMap_mask')){return;}
        var this_obj = $(this).closest(selector);
        height = e.touches[0].pageY - y;
        var this_index = $(this_obj).index();
        if(height<50){
            position = 'up';
            if(this_index < $(selector).length-1){
                $(this_obj).next(selector).css({'zIndex':1,top:'100%'});
            }else if(loop){
                $(selector).first().css({'zIndex':3,top:'100%'});
            }
        }else{
            position = 'down';
            if(this_index > 0){
                $(this_obj).prev(selector).css({'zIndex':3,top:'-100%'});
            }else if(loop){
                $(selector).last().css({'zIndex':3,top:'-100%'});
            }
        }
    }

    function end(e){
        //todo begin_call_back();
        if($(this).hasClass('BMap_mask')){return;}
        var this_obj = $(this).closest(selector);
        var h = Math.abs(Math.abs(e.changedTouches[0].pageY - Math.abs(y)));
        if(h > 50){
            if(typeof begin_call_back == 'function'){
                begin_call_back(this_obj.index())
            }
            if(position == 'up'){
                this_obj.prev(selector).css({zIndex:1,top:0});
                if(this_obj.index() < $(selector).length-1){
                    this_obj.css('zIndex',3).next(selector).css({'zIndex':2,'top':0});
                    this_obj.animate({top:'-100%'},1000,'ease-in-out',function(){
                        $(selector).css('zIndex',1);
                        $(this).next().css('zIndex',2);
                        if(typeof end_call_back == 'function'){
                            end_call_back($(this).next(selector).index());
                        }
                    });
                }else if(loop){
                    $(selector).first().animate({'zIndex':3,top:'0px'},1500,'linear',function(){
                        $(selector).css('zIndex',1);
                        $(this).css('zIndex',2);
                        if(typeof end_call_back == 'function'){
                            end_call_back($(this).index());
                        }
                    });
                }
            }else{
                this_obj.next(selector).css({zIndex:1,top:0});
                if(this_obj.index() > 0){
                    this_obj.prev(selector).animate({'zIndex':3,top:'0px'},1000,'ease-in-out',function(){
                        $(selector).css('zIndex',1);
                        $(this).css('zIndex',2);
                        if(typeof end_call_back == 'function'){
                            end_call_back($(this).index());
                        }
                    });
                }else if(loop){
                    $(selector).last().animate({'zIndex':3,top:'0px'},500,'linear',function(){
                        $(selector).css('zIndex',1);
                        $(this).css('zIndex',2);
                        if(typeof end_call_back == 'function'){
                            end_call_back($(this).index());
                        }
                    });
                }else{
                    if(typeof go_to_start == 'function'){
                        go_to_start.call();
                    }
                }
            }
        }
        height = 0;
        position = null;
    }


    $(function(){
        init_pageH();
        touch.on(selector, 'touchstart', start);
        touch.on(selector, 'touchmove', move);
        touch.on(selector, 'touchend', end);
        touch.on('.container','touchmove',function(e){e.preventDefault();});
        $('input').blur(function(){$('.container').css({top:0})});
/*        touch.on('.down','touchmove',function(){
            toPage(active+1, 800);
        })
        touch.on('.up','touchmove',function(){
            toPage(active-1, 800);
        })*/
    });
})($,touch);

function toPage(index,duration){
    $(selector).eq(index).css({'top':'100%','zIndex':3}).animate({top:'0px'},duration,'linear',function(){
        $(selector).css('zIndex',1);
        $(this).css('zIndex',2);
        end_call_back(index);
    });
}