
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
                                
                                 
                                $('#mainImage').css('width', image.width);
                                $('#mainImage').css('height', image.height);
                                
                                $('#mainImage').css({width: "100%",
                                                    height: "100%"});
                                

                                var middleW = $('#mainImage').width();
                                var newH = image.height / image.width * middleW;

                                var middleH = $('#mainImage').height();
                                var newW = image.width / image.height * middleH;

                                
                                $("#mainImage").css("height", newH.toFixed(0));
                                $("#mainImage").css("width", newW.toFixed(0));
                    
                            });

                            image.src = image_url;
                        }

                    }

                    function makeimage(image1url, image2url) {

                        $("#dragThis").attr("src", image1url);
                        document.getElementById("mainImage").style.backgroundImage = "url(" + image2url + ")";

                        divTouchImg();
                        
                        $('#dragThis').draggable(
                                {
                                    drag: function ()
                                    {
                                        var $this = $(this);
                                        var thisPos = $this.position();
                                        var parentPos = $this.parent().position();

                                        var x = thisPos.left - parentPos.left;
                                        var y = thisPos.top - parentPos.top;


                                        finalposition.width = x;
                                        finalposition.height = y;
                                    },
                                    containment: "parent"

                                });
                                
    
                        $(window).resize(function () {
                            $('.mymainimage').css({height: "100%",
                                width: "100%"});

                        });

                    }

                    function dragging()
                    {
                        var $this = $("#dragThis");
                        var thisPos = $this.position();
                        var parentPos = $this.parent().position();

                        var x = thisPos.left - parentPos.left;
                        var y = thisPos.top - parentPos.top;


                        finalposition.width = x;
                        finalposition.height = y;
                    }



                    $(function () {
                        

                        image1url = "img/demo/amulet96.png";
                        image2url = "img/slide/2.png";                    
                        
                        makeimage(image1url, image2url);

                        


                        var windowWidth = $(window).width();
                        $(window).resize(function () {
                            if (windowWidth !== $(window).width()) {
                                location.reload();
                                return;
                            }
                        });
                        
                        $(window).bind("load", function() {
                            divTouchImg();
                            dragging();
                         });

                    });
