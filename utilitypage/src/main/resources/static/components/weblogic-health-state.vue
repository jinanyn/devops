<template>
    <div style="padding:10px;">
        <el-form :inline="true" :model="mySumbitForm" ref="mySumbitForm" label-width="100px"
                 :label-position="labelPosition" size="mini">
            <el-row>
                <el-col :span="24">
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('mySumbitForm')">开始检测</el-button>
                        <el-button @click="resetForm('mySumbitForm')">重置</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="检测结果" prop="checkResult">
                        <el-input type="textarea" v-model="mySumbitForm.checkResult" :rows="9"  placeholder="检测结果" style="width: 800px;"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
    module.exports = {
        data() {
            return {
                labelPosition: 'left',
                mySumbitForm: {
                    checkResult: ''
                }
            }
        },
        methods: {
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            submitForm(formName) {
                let formData = new FormData();
                axios.get('/utility/weblogic/xxHealthCheck', formData,).
                then(response => (this.mySumbitForm.checkResult=response.data.data))
                    .catch(function (error) { // 请求失败处理
                        console.log(error);
                    });
            }
        },
        mounted() {
        }
    }
</script>