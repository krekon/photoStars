
                    var finalposition = {
                        width: 0,
                        height: 0,
                        relIconWidth: 0,
                        relIconHeight: 0,
                        usersRealImgWidth:0,
                        usersRealImgHeight: 0,
                        usersRelImgWidth: 0,
                        usersRelImgHeight: 0,
                        finalIconWidth: 0,
                        finalIconHeight:  0,
                        finalpositionWidth: 0,
                        finalpositionHeight: 0
                    };
                    
                    

                    function SaveIT()
                    {
                        
                        finalposition.finalIconWidth = finalposition.relIconWidth*finalposition.usersRealImgWidth;
                        finalposition.finalIconHeight = finalposition.relIconHeight*finalposition.usersRealImgHeight;
                        finalposition.finalpositionWidth = finalposition.width*finalposition.usersRealImgWidth;
                        finalposition.finalpositionHeight = finalposition.height*finalposition.usersRealImgHeight;
                        
                        return finalposition;
                        
                    }

                   
                    function makeimage(image1url, image2url) {

                        var image = new Image();
                       
                        image.onload = function () {
                            
                            RealImgDimensions(image.width,image.height);
                            
                            var canvas = document.getElementById("cnvs");
                            canvas.width = this.naturalWidth; // or 'width' if you want a special/scaled size
                            canvas.height = this.naturalHeight; // or 'height' if you want a special/scaled size
                            var context = canvas.getContext('2d');
                            context.drawImage(this, 0, 0);
                            
                            dataUrl = canvas.toDataURL();
                            document.getElementById("cnvs").style.background='url('+dataUrl+')';
                            
                        };

                        image.src = image2url;
                        
                        $("#dragThis").attr("src", image1url);
                            $('#dragThis').draggable(
                                {
                                    drag: function ()
                                    {
                                        var $this = $(this);
                                        var thisPos = $this.position();
                                        
                                        var x = thisPos.left/document.getElementById("cnvs").clientWidth;
                                        var y = thisPos.top/document.getElementById("cnvs").clientHeight;

                                        finalposition.width = x;
                                        finalposition.height = y;
                                    },
                                    containment: "parent"

                                });
                        
                    }
                    
                    function RelativeIconDimensions()
                    {
                        finalposition.relIconWidth = (document.getElementById("dragThis").clientWidth/document.getElementById("cnvs").clientWidth).toFixed(5);
                        finalposition.relIconHeight = (document.getElementById("dragThis").clientHeight/document.getElementById("cnvs").clientHeight).toFixed(5);
                    }
                    
                    function RealImgDimensions(width,height)
                    {
                        finalposition.usersRealImgWidth = width;
                        finalposition.usersRealImgHeight = height;
                    }
                    
                    function RelativeImgDimensions()
                    {
                        finalposition.usersRelImgWidth = document.getElementById("cnvs").clientWidth;
                        finalposition.usersRelImgHeight = document.getElementById("cnvs").clientHeight;
                    }

                    function startingPosition()
                    {
                        var $this = $("#dragThis");
                        var thisPos = $this.position();

                        var x = (thisPos.left/document.getElementById("cnvs").clientWidth).toFixed(5);
                        var y = (thisPos.top/document.getElementById("cnvs").clientHeight).toFixed(5);

                        finalposition.width = x;
                        finalposition.height = y;
                    }

                    $(function () {
                        
                        image1url = "img/demo/amulet96.png";
                        image2url = "img/demo/Bugatti-Chiron-2016.jpg";                    
                        
                        makeimage(image1url, image2url);

                        var windowWidth = $(window).width();
                        $(window).resize(function () {
                            if (windowWidth !== $(window).width()) {
                                location.reload();
                                return;
                            }
                        });
                        
                        $(window).bind("load", function() {
                            RelativeImgDimensions();
                            RelativeIconDimensions();
                            startingPosition();
                         });

                    });
