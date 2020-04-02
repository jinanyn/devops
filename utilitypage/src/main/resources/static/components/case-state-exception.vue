<template>
    <div style = "padding:10px;">
    <el-table
            :data="tableData"
            height="400"
            border
            style="width: 100%">
        <el-table-column
                type="index"
                label="序号"
                width="180">
        </el-table-column>
        <el-table-column
                prop="shenqingh"
                label="申请号"
                width="180">
        </el-table-column>
        <el-table-column
                prop="examiner"
                label="审查员"
                width="180">
        </el-table-column>
        <el-table-column
                prop="comments"
                label="备注">
        </el-table-column>
    </el-table>
    </div>
</template>

<script>
    module.exports ={
        data() {
            return {
                tableData: []
            }
        },
        mounted(){
            axios
                .get('/utility/dataMonitor/caseStateException',{
                    params: { 'parentId': '123456' }
                }).then(response => (this.tableData = response.data))
                .catch(function (error) { // 请求失败处理
                    console.log(error);
                });
        }
    }
</script>