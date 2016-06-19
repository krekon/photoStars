
                    var finalposition = {
                        width: 0,
                        height: 0
                    };

                    function SaveIT()
                    {
                        finalposition.width;
                        finalposition.height;
                    }

                    function hideBtnSP() {
                        $("#divSP").fadeOut("slow");
                    }
                    function showBtnSP() {
                        $("#divSP").fadeIn("slow");

                    }

                    function MoveBtn() {
                        var btnoffset = $("#btnSavePosition").offset();
                        var xPosbtn = btnoffset.left;
                        var yPosbtn = btnoffset.top;

                        var IWidth = document.getElementById('dragThis').clientWidth;
                        var IHeight = document.getElementById('dragThis').clientHeight;

                        var BWidth = document.getElementById('btnSavePosition').clientWidth;
                        var BHeight = document.getElementById('btnSavePosition').clientHeight;

                        if (Math.abs(finalposition.width - xPosbtn) < (IWidth + BWidth / 2) && Math.abs(finalposition.height - yPosbtn) < (IHeight / 2 + BHeight / 2))
                        {
                            if (document.getElementById("divSP").style.left === "-35%")
                            {
                                document.getElementById("divSP").style.left = "35%";
                            }
                            else
                            {
                                document.getElementById("divSP").style.left = "-35%";
                            }

                        }

                    }

                    function divTouchImg()
                    {
                        var image_url = $('#mainImage').css('background-image'),
                                image;


                        image_url = image_url.match(/^url\("?(.+?)"?\)$/);

                        if (image_url[1]) {
                            image_url = image_url[1];
                            image = new Image();


                            $(image).load(function () {


                                middleW = $('#mainImage').width();
                                newH = image.height / image.width * middleW;

                                middleH = $('#mainImage').height();
                                newW = image.width / image.height * middleH;


                                $(".mymainimage").css("height", newH.toFixed(0));
                                $(".mymainimage").css("width", newW.toFixed(0));
                                $(".mymainimage").css("position", "static");
                                $(".mymainimage").css("top", "50%");
                                $(".mymainimage").css("left", "50%");

                                //                        $("#dragThis").css("position", "relative");
                                //                        $("#dragThis").css("top", "-35%");
                                //                        $("#dragThis").css("left", "5%");


                            });

                            image.src = image_url;
                        }

                    }

                    function makeimage(image1url, image2url) {

                        $("#dragThis").attr("src", image1url);
                        document.getElementById("mainImage").style.backgroundImage = "url(" + image2url + ")";

                        var imgWidth = document.getElementById("dragThis").style.width;
                        var imgHeight = document.getElementById("dragThis").style.height;

                        $('#mainImage').css('maxWidth', imgWidth);
                        $('#mainImage').css('maxHeight', imgHeight);

                        $('#dragThis').draggable(
                                {
                                    drag: function ()
                                    {
                                        var offset = $(this).offset();
                                        var xPos = offset.left;
                                        var yPos = offset.top;

                                        finalposition.width = xPos;
                                        finalposition.height = yPos;
                                    },
                                    containment: "parent"

                                });

                        $('#mymainimage').css({height: "100%",
                            width: "100%"});
                        $(window).resize(function () {
                            $('#mymainimage').css({height: "100%",
                                width: "100%"});

                        });

                    }





                    $(function () {

                        image1url = "img/amulet.png";
                        image2url = "img/demo/couple-vacation-tropical-lgn.jpg";
                        makeimage(image1url, image2url);

                        divTouchImg();


                        var windowWidth = $(window).width();
                        $(window).resize(function () {
                            if (windowWidth !== $(window).width()) {
                                location.reload();
                                return;
                            }
                        });

                    });
