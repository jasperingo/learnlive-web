'strict';


var copyInput = document.getElementById("copy-input");
var copyButton = document.getElementById("copy-button");
if (copyButton !== null) {
    copyButton.addEventListener("click", copyFunction);
}

var startButton = document.getElementById("start-stream");
if (startButton !== null) {
    startButton.addEventListener("click", startClass);
}


function copyFunction() {
  
    copyInput.type = "text";
    copyInput.select();
    copyInput.setSelectionRange(0, 99999); 
    document.execCommand("copy");
    copyInput.type = "hidden";
    
    var toast = new bootstrap.Toast(document.getElementById('copyToast'));
    toast.show();
} 


function startClass() {
    var ip = this.dataset.ip;
    /*document.getElementById("classBtn").setAttribute("hidden", "true");
    var elmnt = document.getElementById("c_div");
    var w = elmnt.offsetWidth - 40;
    var h = Math.ceil(w/1.78);*/
    var video = document.getElementById('screen');
    /*video.setAttribute("controls", "controls");
    video.setAttribute("width", w);
    video.setAttribute("height", h);
    document.getElementById("vidCard").setAttribute("style", "height:"+(h+50)+"px");*/
    if(Hls.isSupported()) {
        var hls = new Hls();
        hls.loadSource('http://'+ip+':8282/hls/live/index.m3u8');
        hls.attachMedia(video);
        hls.on(Hls.Events.MANIFEST_PARSED,function() {
            video.play();
        });
    }else if (video.canPlayType('application/vnd.apple.mpegurl')) {
        video.src = 'http://'+ip+':8282/hls/live/index.m3u8';
        video.addEventListener('loadedmetadata',function() {
            video.play();
        });
    }else {
        document.write("Not supported");
    }
    //document.getElementById("vid").removeAttribute("hidden");
}


