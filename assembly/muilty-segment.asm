;多段，数据段加代码段
assume cs:codesg

 codesg segment
  ;以下数据段在程序加载后放到CS所指的0位置处
  dw 0001h,0002h,0003h,0004h
  ;start为代码开始标志，从这读，忽略上面的数据
  start:
    mov bx,0
    mov ax,0
    mov cx,4
  s:add ax,cs:[bx]
    add bx,2
    loop s
    mov ax,4c00h
    int 21h
 codesg ends
;end后多个start
end start