<%--Edit comment tools template--%>
<script id="template_comment_edit" type="text/mustache-template">
    <form id="edit_form_{{id}}" method="post" class="edit_form">
        <textarea name="content" class="form-control">{{content}}</textarea>
        <input type="hidden" name="id" value="{{id}}"/>

        <div class="edit_buttons">
            <button type="button" class="btn btn-success btn-xs" onclick="confirmEditComment('{{id}}')">
                {{localized_confirm}}
            </button>
            <button type="button" class="btn btn-danger btn-xs" onclick="cancelEditComment('{{id}}')">
                {{localized_cancel}}
            </button>
        </div>
    </form>
</script>

<%--Comment body template--%>
<script id="template_comment" type="text/mustache-template">
    <div id="comment_{{id}}" class="comment">
        <div class="user_avatar">
            <img src="{{avatar_src}}" class="avatar" alt="avatar">
        </div>
        <div id="comment_body_{{id}}" class="comment_body">
            <div id="comment_header_{{id}}" class="comment_header">
                <div id="comment_author_{{id}}" class="comment_author">
                    <a href="{{profile_link}}"><span class="author_name">{{author_name}}</span></a>
                </div>
                {{#owner}}
                <div class="comment_manage_buttons">
                    <button type="button" class="btn btn-link" value="{{localized_edit}}"
                            onclick="editComment('{{id}}')">
                        <span class="glyphicon glyphicon-edit"></span>
                    </button>
                    <button type="button" class="btn btn-link" value="{{localized_delete}}"
                            onclick="deleteComment('{{id}}')">
                        <span class="glyphicon glyphicon-remove"></span>
                    </button>
                </div>
                {{/owner}}
            </div>

            <div id="comment_main_{{id}}" class="comment_main">
                <span id="comment_text{{id}}" class="comment_content">{{#appealUserName}}<a href="{{appealProfileLink}}"
                                                                                            class="appeal_Link">{{appealUserName}}</a>,{{appealContent}}{{/appealUserName}}{{^appealUserName}}{{content}}{{/appealUserName}}
                </span>
            </div>
            <div id="comment_footer_{{id}}" class="comment_footer">
                <div class="reply_div">
                    <div class=comment_date>
                        {{date}}
                    </div>
                    {{#can_reply}}
                    <button type="button" class="btn btn-link" onclick="replyComment('{{id}}')">reply</button>
                    {{/can_reply}}
                </div>
                <div class="comment_ratio_panel">
                    <button id=rate_btn_down_{{id}} type="button" class="btn btn-link"
                            onclick="rateComment('down','{{id}}')">
                        <span class="glyphicon glyphicon-arrow-down"></span>
                    </button>

                    <abbr title="-{{ratio_negative}}  +{{ratio_positive}}">
                        <span class="comment_rate">{{summary_ratio}}</span>
                    </abbr>

                    <button id=rate_btn_up_{{id}} type="button" class="btn btn-link"
                            onclick="rateComment('up','{{id}}')">
                        <span class="glyphicon glyphicon-arrow-up"></span>
                    </button>
                </div>
            </div>

        </div>
        <div style="clear:both"></div>
    </div>
</script>