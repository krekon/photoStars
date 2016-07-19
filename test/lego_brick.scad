$fs=0.25;$fa=6;
union() {
union() {
rotate_extrude() translate([200,0,0])circle(r=5, center=true);
;translate([0.0,0.0,0])sphere(r=17.9);
}
rotate([5,0,45])union() {
rotate_extrude() translate([300,0,0])circle(r=5, center=true);
;translate([0.0,0.0,0])sphere(r=28.4);
}
union() {
rotate_extrude() translate([400,0,0])circle(r=5, center=true);
;translate([0.0,0.0,0])sphere(r=28.5);
}
union() {
rotate_extrude() translate([500,0,0])circle(r=5, center=true);
;translate([-175.6516,-468.1309,0])sphere(r=19);
}
union() {
rotate_extrude() translate([600,0,0])circle(r=5, center=true);
;translate([0.0,0.0,0])sphere(r=50);
}
union() {
rotate_extrude() translate([700,0,0])circle(r=5, center=true);
;translate([0.0,0.0,0])union() {
rotate([45,-45,-45])scale([1,1,0])rotate_extrude() translate([44.8,0,0])circle(r=25, center=true);
;sphere(r=44.8);
}
}
union() {
rotate_extrude() translate([800,0,0])circle(r=5, center=true);
;translate([0.0,0.0,0])sphere(r=30.3);
}
union() {
rotate_extrude() translate([900,0,0])circle(r=5, center=true);
;translate([0.0,0.0,0])sphere(r=30);
}
}
