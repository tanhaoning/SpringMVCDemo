/**
 * Created by Roger on 16/2/21.
 */
var ctx = {
    playList: null,
    player: null,
    currentSong: null,
    $needle: null,
    currentIndex: 0,
    $curTime: null,
    $totTime: null,
    $processBtn: null,
    $processBar: null,
    $rdyBar: null,
    $curBar: null,
    $playBtn: null,
    $pauseBtn: null,
    canvas: null,
    backImage: null,
    interval: 0,
    processBtnState: 0,
    originX: 0,
    diskCovers: [],
    isPlaying: false
};

ctx.init = function () {
    ctx.initData();
    ctx.initState();
    ctx.updateSong();
    ctx.setInterval();
    ctx.initProcessBtn(ctx.$processBtn);
    ctx.updateCoverState(0);
};

ctx.initData = function () {
    ctx.currentIndex = +localStorage.getItem("currentSongIndex") || 0;
    ctx.currentIndex >= ctx.playList.length ? ctx.currentIndex = 0 : '';
    ctx.currentSong = ctx.playList[ctx.currentIndex];
    ctx.player = $('#player').get(0);
    ctx.$needle = $("#needle ");
    ctx.$curTime = $("#currentTime");
    ctx.$totTime = $("#totalTime");
    ctx.$processBtn = $('#processBtn');
    ctx.$processBar = $("#process .process-bar");
    ctx.$rdyBar = $("#process .rdy");
    ctx.$curBar = $("#process .cur");
    ctx.$playBtn = $('#controls .play');
    ctx.$pauseBtn = $('#controls .pause');
    ctx.diskCovers = [$('.disk-cover:eq(0)'), $('.disk-cover:eq(1)'), $('.disk-cover:eq(2)')];
};

ctx.updateCoverState = function (derection) {
    var temp, speed = 800,
        preIndex = ctx.currentIndex - 1 < 0 ? ctx.playList.length - 1 : ctx.currentIndex - 1,
        nextIndex = ctx.currentIndex + 2 > ctx.playList.length ? 0 : ctx.currentIndex + 1,
        posLeft = -ctx.diskCovers[0].width() / 2,
        posCenter = '50%',
        posRight = ctx.diskCovers[0].parent().width() + ctx.diskCovers[0].width() / 2;

    if (derection === 1) {
        temp = ctx.diskCovers[0];
        ctx.diskCovers[0] = ctx.diskCovers[1];
        ctx.diskCovers[1] = ctx.diskCovers[2];
        ctx.diskCovers[2] = temp;

        ctx.diskCovers[2].css('left', posRight);
        ctx.diskCovers[1].animate({left: posCenter}, speed);
        ctx.diskCovers[0].animate({left: posLeft}, speed);
    } else if (derection === -1) {
        temp = ctx.diskCovers[2];
        ctx.diskCovers[2] = ctx.diskCovers[1];
        ctx.diskCovers[1] = ctx.diskCovers[0];
        ctx.diskCovers[0] = temp;

        ctx.diskCovers[0].css('left', posLeft);
        ctx.diskCovers[1].animate({left: posCenter}, speed);
        ctx.diskCovers[2].animate({left: posRight}, speed);
    } else {
        ctx.diskCovers[0].css('left', posLeft).show();
        ctx.diskCovers[1].css('left', posCenter).show();
        ctx.diskCovers[2].css('left', posRight).show();
    }

    ctx.diskCovers[0].children('.album').attr('src', ctx.playList[preIndex].album.picUrl);
    ctx.diskCovers[1].children('.album').attr('src', ctx.playList[ctx.currentIndex].album.picUrl);
    ctx.diskCovers[2].children('.album').attr('src', ctx.playList[nextIndex].album.picUrl);

    ctx.changeAnimationState(ctx.diskCovers[0], 'paused');
    ctx.changeAnimationState(ctx.diskCovers[2], 'paused');
};

ctx.changeAnimationState = function ($ele, state) {
    $ele.css({
        'animation-play-state': state,
        '-webkit-animation-play-state': state
    });
};


ctx.initState = function () {
    $('img').attr('draggable', false);
    ctx.player.addEventListener('ended', ctx.next);
    ctx.player.addEventListener('canplay', ctx.readyToPlay);
    window.addEventListener('resize', ctx.updateCoverState);
};

ctx.updateSong = function () {
    ctx.player.src = ctx.currentSong.mp3Url;
    ctx.updatePic();
    ctx.updateMusicInfo();
    if (ctx.isPlaying) {
        setTimeout(ctx.play, 500);
    }
    localStorage.setItem("currentSongIndex", ctx.currentIndex);
};

ctx.updatePic = function () {
    $(".bg").css('background-image', 'url(' + ctx.currentSong.album.picUrl + ')');
};

ctx.updateMusicInfo = function () {
    var names = [];
    $('#songName').html(ctx.currentSong.name);
    $.each(ctx.currentSong.artists, function (i, item) {
        names.push(item.name);
    });
    $('#artist').html(names.join('/'))
};

ctx.play = function () {
    ctx.player.play();
    ctx.isPlaying = true;
    ctx.changeAnimationState(ctx.diskCovers[1], 'running');
    ctx.moveNeedle(true);
    ctx.$playBtn.hide();
    ctx.$pauseBtn.show();
};

ctx.pause = function () {
    ctx.player.pause();
    ctx.isPlaying = false;
    ctx.moveNeedle(false);
    ctx.changeAnimationState(ctx.diskCovers[1], 'paused');
    ctx.$playBtn.show();
    ctx.$pauseBtn.hide();
};

ctx.moveNeedle = function (play) {
    if (play) {
        ctx.$needle.removeClass("pause-needle").addClass("resume-needle");
    } else {
        ctx.$needle.removeClass("resume-needle").addClass("pause-needle");
    }
}

ctx.next = function () {
    ctx.currentIndex = ctx.currentIndex < ctx.playList.length - 1 ? ctx.currentIndex + 1 : 0;
    ctx.currentSong = ctx.playList[ctx.currentIndex];
    ctx.moveNeedle(false);
    ctx.updateCoverState(1);
    ctx.player.pause();
    setTimeout(ctx.updateSong, 1200);
};

ctx.prev = function () {
    ctx.currentIndex = ctx.currentIndex > 1 ? ctx.currentIndex - 1 : ctx.playList.length - 1;
    ctx.currentSong = ctx.playList[ctx.currentIndex];
    ctx.player.pause();
    ctx.moveNeedle(false);
    ctx.updateCoverState(-1);
    setTimeout(ctx.updateSong, 1200);
};

ctx.updateProcess = function () {
    var buffer = ctx.player.buffered,
        bufferTime = buffer.length > 0 ? buffer.end(buffer.length - 1) : 0,
        duration = ctx.player.duration,
        currentTime = ctx.player.currentTime;
    ctx.$totTime.text(validateTime(duration / 60) + ":" + validateTime(duration % 60));
    ctx.$rdyBar.width(bufferTime / duration * 100 + '%');
    if (!ctx.processBtnState) {
        ctx.$curBar.width(currentTime / duration * 100 + '%');
        ctx.$curTime.text(validateTime(currentTime / 60) + ":" + validateTime(currentTime % 60));
    }
};

ctx.setInterval = function () {
    if (!ctx.interval) {
        ctx.updateProcess();
        ctx.interval = setInterval(ctx.updateProcess, 1000);
    }
};

ctx.clearInterval = function () {
    if (ctx.interval) {
        clearInterval(ctx.interval);
    }

};

ctx.initProcessBtn = function ($btn) {
    var moveFun = function () {
            var e = event, duration = ctx.player.duration,
                totalWidth = ctx.$processBar.width(), percent, moveX, newWidth;
            e.preventDefault();
            if (ctx.processBtnState) {
                moveX = (e.clientX || e.touches[0].clientX) - ctx.originX;
                newWidth = ctx.$curBar.width() + moveX;

                if (newWidth > totalWidth || newWidth < 0) {
                    ctx.processBtnState = 0;
                } else {
                    percent = newWidth / totalWidth;
                    ctx.$curBar.width(newWidth);
                    ctx.$curTime.text(validateTime(percent * duration / 60) + ":" + validateTime(percent * duration % 60));
                }
                ctx.originX = (e.clientX || e.touches[0].clientX);
            }
        },
        startFun = function () {
            var e = event;
            ctx.processBtnState = 1;
            ctx.originX = (e.clientX || e.touches[0].clientX);
        },
        endFun = function () {
            if (ctx.processBtnState) {
                ctx.player.currentTime = ctx.$curBar.width() / ctx.$processBar.width() * ctx.player.duration;
                ctx.processBtnState = 0;
                ctx.updateProcess();
            }
        };
    $btn.on('mousedown', startFun).on('touchstart', startFun);
    $("body").on('mouseup', endFun).on('touchend', endFun);
    $("#process").on('mousemove', moveFun).on('touchmove', moveFun);
}


function validateTime(number) {
    var value = (number > 10 ? number + '' : '0' + number).substring(0, 2);
    return isNaN(value) ? '00' : value;
}

$(function () {
    //var url = location.href.indexOf("localhost") !== -1 ? '../resource/play_list.json' : './playlist.php?id=173934373';
    var url = "/SpringMVCDemo/test/getPlayResource";
    var param = {
        "test":"Fuck",
        "msg":"is no thing"
    }

    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        data:param,
        timeout:5000,
        beforeSend:function(xhr){
        },
        success: function (data) {
            if (null == data) {
                return;
            }
            data = JSON.parse(data.data);
            ctx.playList = data.result.tracks;
            ctx.init();
        },
        error: function (msg) {
            alert(msg);
        }
    });
});




