$fs=0.25;$fa=6;
union() {
sphere(r=100);
union() {
rotate_extrude() translate([200,0,0])circle(r=5, center=true);
;translate([-96.0343,130.6322,19.4845])sphere(r=17.9);
}
rotate([5,0,45])union() {
rotate_extrude() translate([300,0,0])circle(r=5, center=true);
;translate([-176.9523,202.7398,12.9917])sphere(r=28.4);
}
union() {
rotate_extrude() translate([400,0,0])circle(r=5, center=true);
;translate([118.0913,-350.3125,0.0132])sphere(r=28.5);
}
union() {
rotate_extrude() translate([500,0,0])circle(r=5, center=true);
;translate([-10.9737,-404.9479,-8.2158])sphere(r=19);
}
union() {
rotate_extrude() translate([600,0,0])circle(r=5, center=true);
;translate([-582.4011,25.0329,12.9319])sphere(r=50);
}
union() {
rotate_extrude() translate([700,0,0])circle(r=5, center=true);
;translate([-203.2221,-709.4844,20.4261])union() {
rotate([45,-45,-45])scale([1,1,0])rotate_extrude() translate([44.8,0,0])circle(r=25, center=true);
;sphere(r=44.8);
}
}
union() {
rotate_extrude() translate([800,0,0])circle(r=5, center=true);
;translate([708.7697,275.3571,-8.1629])sphere(r=30.3);
}
union() {
rotate_extrude() translate([900,0,0])circle(r=5, center=true);
;translate([844.8622,-306.2297,-13.1633])sphere(r=30);
}
}
