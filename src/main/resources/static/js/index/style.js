

    //控制滚动
    window.onscroll = function(){
        var t = document.documentElement.scrollTop || document.body.scrollTop;
        console.log(t);
        var oTp = document.getElementById( "tp" );
        var oDn = document.getElementById( "dnav" );
        var oLg = document.getElementById( "logo_o" );
        var oEe = document.getElementById( "ee" );
        if( t >= 300 ) {
            oTp.className = 'row navbar-fixed-top top_c';
            oLg.className = 'logo_n';
            oDn.className = 'navbar navbar-default navbar-fixed-top';
            oEe.style.backgroundColor = "#F3F3F3";
        } else {
            oTp.className = 'row';
            oLg.className = 'logo_o';
            oDn.className = 'navbar navbar-default odn';
            oEe.style.backgroundColor = "rgba(0, 0, 0, 0)";
        }
    };
    
