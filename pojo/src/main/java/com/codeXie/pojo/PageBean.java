package com.codeXie.pojo;

import java.util.List;

public class PageBean<T>{
    private int size = 5; //每页显示记录条数
    private int index = 1; //当前页号
    private int totalPageCount = 1; //总页数
    private int totalCount = 0; //记录总数

    private int[] numbers;  //展示页面集合
    private List<T> list; //要显示到页面的数据集

    /**
     * 得到起始记录
     * @return
     */
    public int getStartRow(){
        return (index-1)*size;
    }

    /**
     * 得到结束记录
     * @return
     */
    public int getEndRow(){
        return index*size;
    }

    public int getSize() {
        return size;
    }

    public int getIndex() {
        return index;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public List<T> getList() {
        return list;
    }

    public void setSize(int size){
        if(size>0){
            this.size = size;
        }
    }

    public void setIndex(int index){
        if(index>0){
            this.index = index;
        }
    }

    /**
     * 设置总记录数，并更新totalPageCount和numbers
     * @param count
     */
    public void setTotalCount(int count){
        if(count > 0){
            this.totalCount = count;
            setTotalPageCountByRs();

        }
    }

    /**
     * 根据记录总数计算总页数，并更新展示页面集合
     */
    private void setTotalPageCountByRs(){
        if (this.size > 0 && this.totalCount > 0 && this.totalCount % this.size == 0) {
            this.totalPageCount = this.totalCount / this.size;
        } else if (this.size > 0 && this.totalCount > 0 && this.totalCount % this.size > 0) {
            this.totalPageCount = (this.totalCount / this.size) + 1;
        } else {
            this.totalPageCount = 0;
        }
        setNumbers(totalPageCount);//获取展示页数集合
    }
    public void setNumbers(int totalPageCount){
        if(totalPageCount>0){
            int[] numbers = new int[totalPageCount>10?10:totalPageCount];
            int k=0;
            for(int i=0; i<totalPageCount;i++){
                if((i>=index-(numbers.length/2+1)||i>=totalPageCount-numbers.length) && k<numbers.length){
                    numbers[k++] = i+1;
                }else if(k>=numbers.length){
                    break;
                }
            }
            this.numbers = numbers;
        }
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
