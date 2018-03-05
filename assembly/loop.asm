;plus the number at 0:200 memory unit and move it to ax
;demo0:2003ax
assume cs:codesg
 codesg segment
   mov ax,0
   mov ds,ax
   mov bx,200h
   mov ax,[bx];mov ax,[200h]200h
   mov cx,3
 s:add ax,ax
   loop s
   mov ax,4c00h
   int 21h
 codesg ends
end
