<template>
    <div style="padding:10px;">
        <el-form :inline="true" :model="mySumbitForm" ref="mySumbitForm" label-width="100px"
                 :label-position="labelPosition" size="mini">
            <el-row>
                <el-col :span="16">
                    <el-form-item label="redis1键" prop="redisKey">
                        <el-input v-model="mySumbitForm.redisKey" placeholder="只支持:申请号_100003和申请号_100005" style="width: 600px;"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item>
                        <el-button type="primary" @click="submitForm('mySumbitForm')">查询</el-button>
                        <el-button @click="resetForm('mySumbitForm')">重置</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="16">
                    <el-form-item label="转换1结果" prop="operResult">
                        <el-input type="textarea" v-model="mySumbitForm.operResult" :rows="9" placeholder="转换结果" style="width: 600px;"></el-input>
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
                    redisKey: '',
                    operResult: ''
                }
            }
        },
        methods: {
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            submitForm(formName) {
                let formData = new FormData();
                formData.append("redisKey", this.mySumbitForm.redisKey);
                axios.post('/utility/imgOperate/cmykToRgb', formData,).
                then(response => (this.mySumbitForm.redisVal=response.data.data))
                    .catch(function (error) { // 请求失败处理
                        if (error.response) {
                            console.log(error.response.data.message);
                            //console.log(error.response.status);
                            //console.log(error.response.headers);
                        } else if (error.request) {
                            console.log('request='+error.request);
                        } else {
                            // Something happened in setting up the request that triggered an Error
                            console.log('Error', error.message);
                        }
                    });
            }
        },
        mounted() {
        }
    }
</script>