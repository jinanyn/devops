<template>
    <div style="padding:10px;">
        <el-form :inline="true" :model="mySumbitForm" ref="mySumbitForm">
            <el-row>
                <el-form-item
                        label="服务器文件路径" prop="serverFilePath">
                    <el-input v-model="mySumbitForm.serverFilePath" placeholder="请输入内容"
                              style="width: 700px;"></el-input>
                </el-form-item>
                <el-form-item
                        label="">
                    <div style="height:40px;">
                        <el-upload
                                class="upload"
                                ref="upload"
                                :limit="1"
                                action="uploadUrl"
                                :file-list="fileList"
                                :auto-upload="false"
                                :http-request="uploadFile"
                                :on-change="handleChange"
                                :on-preview="handlePreview"
                                :on-remove="handleRemove">
                            <el-button slot="trigger" size="small" type="primary" plain>选取2文件</el-button>
                        </el-upload>
                    </div>
                </el-form-item>
            </el-row>
            <el-row>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('mySumbitForm')">提交</el-button>
                    <el-button type="primary" @click="downloadFile('mySumbitForm')">文件下载</el-button>
                    <el-button @click="resetForm('mySumbitForm')">重置</el-button>
                </el-form-item>
            </el-row>
        </el-form>
    </div>
</template>

<script>
    module.exports = {
        data() {
            return {
                uploadUrl: '',
                fileList: [],
                mySumbitForm: {
                    serverFilePath: ''
                }
            }
        },
        methods: {
            resetForm(formName) {
                console.log('aaaa');
                this.$refs[formName].resetFields();
            },
            delFile() {
                this.fileList = [];
            },
            handleChange(file, fileList) {
                this.fileList = fileList;
            },
            uploadFile(file) {
                this.formData.append("file", file.file);
            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePreview(file) {
                console.log(file);
            },
            submitForm(formName) {
                console.log(this.fileList);
                if (this.fileList === undefined || this.fileList === null || this.fileList.length == 0) {
                    alert("请选择上传文件")
                    return;
                }
                let formData = new FormData();
                this.fileList.forEach(function (file) {
                    formData.append("files", file.raw);
                });
                let config = {
                    'Content-Type': 'multipart/form-data'
                };
                formData.append("serverFilePath", this.mySumbitForm.serverFilePath);
                axios.post('/utility/fileOperate/serverFileSubstitute', formData, config).
                then(response => (console.log(response.data)))
                    .catch(function (error) { // 请求失败处理
                        console.log(error);
                    });

            },
            downloadFile(formName){
                axios.get('/utility/fileOperate/serverFiledownload?serverFilePath='+this.mySumbitForm.serverFilePath).
                then(response => (window.open(response.data)))
                    .catch(function (error) { // 请求失败处理
                        console.log(error.response.data.message);
                    });
            }
        },
        mounted() {
        }
    }
</script>