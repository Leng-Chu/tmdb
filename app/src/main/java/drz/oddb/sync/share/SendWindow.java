package drz.oddb.sync.share;

import java.util.ArrayList;

public class SendWindow {
    private final int maxNum;//数组最大长度，即所能容纳的最大请求数量

    private int nextIndex = 0;//数组中下一个待填入元素的索引值，即队尾指针

    private WindowEntry[] syncQueue;

    private int left = 0;//窗口左端索引

    private int windowSize;//窗口大小，即能够发送的区域的大小

    private int right;//窗口右端索引

    private int currentIndex;//当前窗口内正在被处理的元素的索引

    // 整个数据区可以被发送的区间为[left,right]
    // 数据区最后一个元素的索引为nextIndex - 1
    // 可发送的区间[left,right]中[left,currentIndex)内的元素已经完成发送，等待判定通过后移出数据区

    public SendWindow(int maxNum, int windowSize) {
        this.maxNum = maxNum;
        this.windowSize = windowSize;
        this.currentIndex = this.left;
        right = left + windowSize - 1;
        syncQueue = new WindowEntry[maxNum];
    }

    public WindowEntry[] getSyncQueue() {
        return syncQueue;
    }

    public int getLeft() {
        return left;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getRight() {
        return right;
    }



    public boolean isEmpty(){
        return (nextIndex == left);
    }

    //牺牲一个元素空间用于判断整个数据区是否已满
    public boolean isFull(){
        return (((nextIndex + 1) % maxNum) == left);
    }


    public void put(RequestType requestType, Long key){


        WindowEntry entry = new WindowEntry(requestType,key);
        syncQueue[nextIndex] = entry;

        nextIndex = (nextIndex + 1) % maxNum;


    }

    //移除窗口首部元素
    public void remove(){
        left = (left + 1) % maxNum;
        right = (right + 1) % maxNum;
    }

    //获取整个队列首部的元素，但不移出首部元素
    public WindowEntry getFrontEntry(){
        return syncQueue[left];
    }

    //获取下一个待发送的元素
    public WindowEntry getNextEntry(){
        int i = currentIndex;

        if(i != right)
            currentIndex= (currentIndex+1)%maxNum;
        return syncQueue[i];
    }

    //获取整个队列尾部的元素
    public WindowEntry getTailEntry(){
        return syncQueue[nextIndex - 1];
    }

    //获取滑动窗口内的所有元素，即数据区内当前能够被发送的元素
    public ArrayList<WindowEntry> getAllEntries(){
        ArrayList<WindowEntry> result = new ArrayList<>(windowSize);

        int count = 0;
        int i = left;
        while (count < windowSize){
            result.add(syncQueue[i]);
            i=(i+1)%maxNum;
            count++;
        }

        return result;
    }
}