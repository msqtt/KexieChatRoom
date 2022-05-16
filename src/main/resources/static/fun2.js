function animate (obj,orgin,target,callback) {
    clearInterval(obj.timer);
    obj.style.left = orgin+ 'px'
    obj.timer= setInterval(function() {
        // var step=(target-obj.offsetLeft)/5;
        // step=step>0?Math.ceil(step) : Math.floor(step);
        var step = -2
    target=target>0?Math.ceil(target) : Math.floor(target);
    
  if(obj.offsetLeft<=target) 
    // if( obj.offsetLeft>=target)
    {
      obj.style.left=target ;
      clearInterval( obj.timer);
  }
  obj.style.left= obj.offsetLeft+step+'px';
    // len=len-Math.abs(step);
} , 10);
}